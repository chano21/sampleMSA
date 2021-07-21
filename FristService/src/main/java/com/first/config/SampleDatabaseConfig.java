/**
 * 
 */
package com.first.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chano
 * @Date : 2021. 5. 13.
 * @Description : sample database configuration
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "sampleEntityManager", transactionManagerRef = "sampleTransactionManager", basePackages = "com.first.repo")
public class SampleDatabaseConfig {
	
	@Value("${spring.sample.datasource.url}")
	private String sampleDataBaseUrl;
	
	@Value("${spring.sample.datasource.username}")
	private String sampleDatabaseUser;
	
	@Value("${spring.sample.datasource.password}")
	private String sampleDatabasePassword;
	
	@Value("${spring.sample.datasource.driverClassName}")
	private String sampleDatabaseDriver;
	
	@Value("${spring.sample.hibernate.hbm2ddl.auto}")
	private String hbm2ddl;
	
	@Value("${spring.sample.hibernate.dialect}")
	private String dialect;
	
	
	
    @Bean
    public DataSource sampleDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(sampleDatabaseDriver);
        dataSource.setUrl(sampleDataBaseUrl);
        dataSource.setUsername(sampleDatabaseUser);
        dataSource.setPassword(sampleDatabasePassword);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sampleEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties = new HashMap<>();
        localContainerEntityManagerFactoryBean.setDataSource(sampleDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] { "com.first.domain" });
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);       
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
        properties.put("hibernate.dialect", dialect);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager sampleTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sampleEntityManager().getObject());
        return transactionManager;
    }
}