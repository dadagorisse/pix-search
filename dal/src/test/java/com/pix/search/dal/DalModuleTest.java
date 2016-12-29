package com.pix.search.dal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class DalModuleTest {

    @Test
    public void testInject() {
        Injector injector = Guice.createInjector(
                new DalModule());
        DataSourceProvider dataSourceProvider = injector.getInstance(DataSourceProvider.class);

        assertThat(dataSourceProvider, is(notNullValue()));
    }
}
