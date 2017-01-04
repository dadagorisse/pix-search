package com.pix.search.dal.views;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.pix.search.dal.DataAccessException;
import com.pix.search.dal.EntityNotFoundException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseView<T> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseView.class);

    protected final QueryRunner queryRunner;

    protected BaseView(DataSource dataSource) {
        queryRunner = new QueryRunner(dataSource);
    }

    protected abstract Function<ResultSet, T> resultSetParser();

    protected List<T> getAll(String query, Object... parameters) {
        try {
            return queryRunner.query(query, getResultListHandler(), parameters);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    protected Optional<T> getOne(String query, Object... parameters) {
        try {
            return Optional.ofNullable(
                    queryRunner.query(query, getResultSetHandler(), parameters));
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    protected T getOneOrThrow(final String errMsg, String storedProcedureName, Object... parameters) {
        return getOne(storedProcedureName, parameters).<EntityNotFoundException>orElseThrow(
            () -> {
                throw new EntityNotFoundException(errMsg);
            });
    }

    protected <R> R insertAndGetGeneratedKey(String query, Object... parameters) {
        try {
            return queryRunner.insert(query, new ScalarHandler<R>(), parameters);
        }
        catch (SQLException e) {
            LOG.error(String.format("Failed while inserting query: %s", query), e);
            throw new DataAccessException(e);
        }
    }

    protected int save(String query, Object... parameters) {
        try {
            return queryRunner.update(query, parameters);
        } catch (SQLException e) {
            LOG.error(String.format("Failed while executing stored procedure: %s", query), e);
            throw new DataAccessException(e);
        }
    }

    protected void script(Path path) {
        try(InputStream stream =
                    getClass().getClassLoader().getResourceAsStream(path.toString())) {
            save(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)));
        } catch (IOException e) {
            LOG.error(String.format("Failed while executing script from path: %s", path), e);
            throw new DataAccessException(e);
        }
    }

    private ResultSetHandler<T> getResultSetHandler() {
        return rs -> {
            if (rs.next()) {
                return resultSetParser().apply(rs);
            } else {
                return null;
            }
        };
    }

    private AbstractListHandler<T> getResultListHandler() {
        return new AbstractListHandler<T>() {
            @Override
            protected T handleRow(ResultSet rs) throws SQLException {
                return resultSetParser().apply(rs);
            }
        };
    }
}
