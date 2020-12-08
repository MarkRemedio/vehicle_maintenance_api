package com.vehicle.repair.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfig {

	@Value("${database.username}")
	private String dbUsername;

	@Value("${database.password}")
	private String dbPassword;

	@Value("${database.url}")
	private String dbUrl;

	@Bean
	@Profile("local")
	public DataSource getLocalDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url(dbUrl)
				.username(dbUsername)
				.password(dbPassword);

		return dataSourceBuilder.build();
	}

	@Bean
	@Profile("dev")
	public DataSource getDevDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url(dbUrl)
				.username(dbUsername)
				.password(dbPassword);

		return dataSourceBuilder.build();
	}

	@Bean
	@Profile("prod")
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url(dbUrl)
				.username(dbUsername)
				.password(dbPassword);

		return dataSourceBuilder.build();
	}
}
