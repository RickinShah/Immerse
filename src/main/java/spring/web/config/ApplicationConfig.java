package spring.web.config;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUsername("rickin");
		dataSource.setPassword("");
		dataSource.setUrl(
		  "jdbc:mariadb://localhost:3306/Immerse?createDatabaseIfNotExist=true"); 

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPackagesToScan("spring");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setJpaProperties(additionalProperties());
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
	    Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", "update");
	    properties.setProperty("hibernate.show_sql", "true");
	       
	    return properties;
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		ResourceBundle rd = ResourceBundle.getBundle("mail");
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(rd.getString("mail.smtp.host"));
		mailSender.setPort(Integer.parseInt(rd.getString("mail.smtp.port")));
		mailSender.setUsername(rd.getString("email"));
		mailSender.setPassword(rd.getString("password"));
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", rd.getString("mail.transport.protocol"));
		props.put("mail.smtp.auth", rd.getString("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable", rd.getString("mail.smtp.starttls.enable"));
		props.put("mail.smtp.ssl.enable", rd.getString("mail.smtp.ssl.enable"));

		return mailSender;
	}
	
	
}
