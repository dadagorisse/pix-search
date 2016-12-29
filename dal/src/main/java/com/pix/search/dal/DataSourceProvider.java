package com.pix.search.dal;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pix.search.dal.db.JdbcConfiguration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class DataSourceProvider implements Provider<DataSource> {

    private final ComboPooledDataSource cpds;
    private final JdbcConfiguration jdbcConfiguration;

    @Inject
    public DataSourceProvider(ComboPooledDataSource cpds, JdbcConfiguration jdbcConfiguration) {
        this.cpds = cpds;
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public DataSource get() {
        try {
            cpds.setDriverClass(jdbcConfiguration.getDriver().getName());
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Unable to initialize datasource", e);
        }
        cpds.setJdbcUrl(jdbcConfiguration.getUrl());
        cpds.setUser(jdbcConfiguration.getUser());
        cpds.setPassword(jdbcConfiguration.getPassword());

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);

        return cpds;
    }
}
