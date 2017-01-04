package com.pix.search.dal.views;

import com.google.common.collect.Range;
import com.google.inject.Inject;
import com.pix.search.dal.DataAccessException;
import com.pix.search.dal.entities.Image;
import com.pix.search.dal.utils.TimesHelper;
import com.pix.search.dal.utils.WhereClauseHelper;

import javax.sql.DataSource;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class ImageView extends BaseView<Image> {

    @Inject
    public ImageView(DataSource dataSource) {
        super(dataSource);
    }

    public Image get(int id) {
        return getOneOrThrow("Image not found", "SELECT * FROM IMAGE where ID=?", id);
    }

    public List<Image> getWhere(Range<LocalDateTime> dateRange) {
        return getAll("SELECT * FROM IMAGE" +
                WhereClauseHelper.conditions("CREATED_DATE", dateRange,
                        d -> Long.toString(TimesHelper.toTimeStamp(d))));
    }

    public Image put(int width, int height, LocalDateTime date) {
        int id = insertAndGetGeneratedKey(
                "INSERT INTO IMAGE (WIDTH, HEIGHT, CREATED_DATE) VALUES (?,?,?)",
                width,
                height,
                TimesHelper.toTimeStamp(date));
        return new Image(id, width, height, date);
    }

    public void createTableIfNotExists() {
        script(Paths.get("tables","image.sql"));
    }

    @Override
    protected Function<ResultSet, Image> resultSetParser() {
        return rs -> {
            try {
                return new Image(
                        rs.getInt("ID"),
                        rs.getInt("WIDTH"),
                        rs.getInt("HEIGHT"),
                        TimesHelper.fromTimeStamp(rs.getLong("CREATED_DATE")));
            } catch (SQLException e) {
                throw new DataAccessException("Can't convert ResultSet to Image", e);
            }
        };
    }
}
