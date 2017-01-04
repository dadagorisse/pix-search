package com.pix.search.dal.views;

import com.google.common.collect.Range;
import com.pix.search.dal.EntityNotFoundException;
import com.pix.search.dal.entities.Image;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

public class ImageViewTest {

    private final ImageView view;

    public ImageViewTest()  {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        view = new ImageView(dataSource);
    }

    @Test
    public void checkCanCreatePutGet() {
        view.createTableIfNotExists();
        Image created = view.put(1024, 2048, LocalDateTime.of(2016, 12, 18, 3, 31));
        Image retrieved = view.get(created.getId());

        assertThat(created, is(not(nullValue())));
        assertThat(created, samePropertyValuesAs(retrieved));
    }

    @Test(expected = EntityNotFoundException.class)
    public void checkImageNotFound() {
        view.createTableIfNotExists();
        view.get(9999999);
    }

    @Test
    public void checkSelectWithDateRange() {
        view.createTableIfNotExists();
        view.put(1024, 2048, LocalDateTime.of(2010, 12, 18, 3, 31));
        Image expected1 = view.put(1, 1, LocalDateTime.of(2012, 12, 18, 3, 31));
        Image expected2 = view.put(2, 2, LocalDateTime.of(2014, 12, 18, 3, 31));
        view.put(1024, 2048, LocalDateTime.of(2016, 12, 18, 3, 31));

        List<Image> actual = view.getWhere(Range.closed(
                LocalDateTime.of(2011, 1, 1, 1, 1),
                LocalDateTime.of(2015, 1, 1, 1, 1)));

        assertThat(actual, containsInAnyOrder(
                samePropertyValuesAs(expected1),
                samePropertyValuesAs(expected2)));
    }
}
