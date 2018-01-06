package com.stackroute.activitystream.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport 
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * 
 * */

@EnableWebMvc
@Configuration
@ComponentScan("com.stackroute.activitystream")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ApplicationContextConfig {

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the dataSource.
	 * To create the DataSource bean, we need to know:
	 * 1. Driver class name
	 * 2. Database URL
	 * 3. Username
	 * 4. Password
	 */@Bean
		public DataSource getDataSource()
		{
			BasicDataSource dataSource=new BasicDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/activity3");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			return dataSource;
		}
		
		
	
	
	
	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory class 
	 * through which we get sessions and perform database operations. 
	 */
	
	 @Bean
		public SessionFactory getSessionFactory(DataSource dataSource)
		{
			LocalSessionFactoryBuilder sessionFactoryBuilder=new LocalSessionFactoryBuilder(dataSource);
			sessionFactoryBuilder.scanPackages("com.stackroute.activitystream");
			sessionFactoryBuilder.addProperties(getProperties());
			return sessionFactoryBuilder.buildSessionFactory();
		}
		
		public Properties getProperties()
		{
			Properties properties=new Properties();
			properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
			properties.setProperty("hibernate.format_sql", "true");
			properties.setProperty("hibernate.hbm2ddl.auto", "update");
			properties.setProperty("hibernate.show_sql","true");
			return properties;
		}

		
	
	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles transaction 
	 * in Spring. The application that uses single hibernate session factory for database transaction
	 * has good choice to use HibernateTransactionManager. HibernateTransactionManager can work with 
	 * plain JDBC too. HibernateTransactionManager allows bulk update and bulk insert and ensures 
	 * data integrity.   
	 */
		@Bean
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
		{
			return new HibernateTransactionManager(sessionFactory);
		}
		
		
		
	

}
