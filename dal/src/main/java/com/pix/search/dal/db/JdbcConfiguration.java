package com.pix.search.dal.db;

public class JdbcConfiguration {

    private final String url;
    private final String user;
    private final String password;
    private final Class driver;

    public JdbcConfiguration(String url, String user, String password, Class driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Class getDriver() {
        return driver;
    }
}
