package in.glootech.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

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
		    entityManagerFactoryRef = "secondEntityManagerFactoryBean",
			basePackages = "in.glootech.second.dao",
			transactionManagerRef = "secondTransactionManagerBean"
		)
public class MysqlConfig {
	//datasource
	
	@Autowired
	private Environment environment;
	
	@Primary
	@Bean(name = "secondDatasource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.second.datasource.driverClassName"));
		dataSource.setUrl(environment.getProperty("spring.second.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.second.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.second.datasource.password"));
		
		return dataSource;
		
	}
	
	
	
	//entitymanagerfactory
	
	@Primary
	@Bean(name = "secondEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("in.glootech.second.entity");
		
		Map<String, String> jpaProps = new HashMap<>();
		jpaProps.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProps.put("hibernate.show_sql", "true");
		jpaProps.put("hibernate.hbm2ddl.auto", "update");
		
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(jpaVendorAdapter);
		
		bean.setJpaPropertyMap(jpaProps);
		
		return bean;
	}
	
	
	
	//platformtransactionmanager
	@Primary
	@Bean(name = "secondTransactionManagerBean")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return manager;
		
	}
}
