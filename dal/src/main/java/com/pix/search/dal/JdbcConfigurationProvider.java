package com.pix.search.dal;

import com.google.inject.Provider;
import com.pix.search.dal.db.JdbcConfiguration;
import com.typesafe.config.Config;

public class JdbcConfigurationProvider implements Provider<JdbcConfiguration> {

    private final Config config;

    public JdbcConfigurationProvider(Config config) {
        this.config = config;
    }

    public JdbcConfiguration get() {

        Config jdbcConf = config.getConfig("jdbc");

        try {
            return new JdbcConfiguration(
                    jdbcConf.getString("url"),
                    jdbcConf.getString("user"),
                    jdbcConf.getString("password"),
                    Class.forName(jdbcConf.getString("driver")));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC driver class not found", e);
        }
    }
}
