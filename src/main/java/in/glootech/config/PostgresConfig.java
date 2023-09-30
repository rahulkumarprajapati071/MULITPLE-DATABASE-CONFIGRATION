package in.glootech.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
			entityManagerFactoryRef = "entityTransactionManagerBean",
			basePackages = "in.glootech.dao",
			transactionManagerRef = "transactionManager"
		)
public class PostgresConfig {
	
	@Autowired
	private Environment environment;
	
	//datasource
	
	@Bean
	@Primary
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		
		return dataSource;
	}
	
	
	//entityManagerFactory
	@Primary
	@Bean(name = "entityTransactionManagerBean")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("in.glootech.entity");
		
		
		Map<String, String> jpaProps = new HashMap<>();
		jpaProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		jpaProps.put("hibernate.show_sql", "true");
		jpaProps.put("hibernate.hbm2ddl.auto", "update");
		
		bean.setJpaPropertyMap(jpaProps);
		
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(adapter);
		
		return bean;
	}
	
	
	//PlatformTransactionManager
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return manager;
	}
}
