package com.pix.search.dal;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.typesafe.config.ConfigFactory;

public class DalModule extends AbstractModule {

    protected void configure() {
        bind(ComboPooledDataSource.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    JdbcConfigurationProvider jdbcConfigurationProvider() {
        return new JdbcConfigurationProvider(ConfigFactory.load());
    }

    @Provides
    @Singleton
    DataSourceProvider dataSourceProvider(ComboPooledDataSource cpds, JdbcConfigurationProvider jdbcConfigurationProvider) {
        return new DataSourceProvider(cpds, jdbcConfigurationProvider.get());
    }
}
