package com.gobbledygook.gobbledygook;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Autowired
Environment env;



@Bean 
public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new 
    DriverManagerDataSource();

    dataSource.setDriverClassName(env.getProperty("driverClassName"));
    dataSource.setUrl(env.getProperty("url"));
    dataSource.setUsername(env.getProperty("user"));
    dataSource.setPassword(env.getProperty("password"));
    return dataSource;
}
