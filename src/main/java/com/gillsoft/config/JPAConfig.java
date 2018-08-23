package com.gillsoft.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.gillsoft.repository")
public class JPAConfig {

	@Autowired
	private Environment env;

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setDataSource(getDataSource());
		lcemfb.setPersistenceUnitName("stations");
		lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
		lcemfb.setJpaProperties(jpaProperties());
		return lcemfb;
	}

	@Bean
	public JpaVendorAdapter getJpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
		dataSource.setUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));
		return dataSource;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager txManager() {
		return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		properties.put("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));
		
		/*properties.put("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		properties.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		properties.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		properties.put("hibernate.c3p0.acquire_increment", env.getProperty("hibernate.c3p0.acquire_increment"));
		properties.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));*/
		return properties;
	}
}
