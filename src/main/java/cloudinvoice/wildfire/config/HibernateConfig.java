package cloudinvoice.wildfire.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

import cloudinvoice.wildfire.util.TenantAwareRoutingSource;

@Configuration
public class HibernateConfig {
	
	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/*@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
		
		Map<String, Object> properties = new HashMap<>();
		properties.putAll(jpaProperties.getHibernateProperties(dataSource));
		properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
		properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("cloudinvoice.wildfire");
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setJpaPropertyMap(properties);
		return em;
	}*/
	
	@Bean
    public DataSource dataSource() {
        AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("icicibank", icicibank());
        targetDataSources.put("hdfcbank", hdfcbank());
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    }

    public DataSource icicibank() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(5);
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/icicibank");
        dataSource.addDataSourceProperty("user", "commuser");
        dataSource.addDataSourceProperty("password", "Test@123");

        return dataSource;
    }

    public DataSource hdfcbank() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(5);
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/hdfcbank");
        dataSource.addDataSourceProperty("user", "commuser");
        dataSource.addDataSourceProperty("password", "Test@123");

        return dataSource;
    }

    private static Properties getDefaultProperties() {
        Properties defaultProperties = new Properties();
        // Set sane Spring Hibernate properties:
        defaultProperties.put("spring.jpa.show-sql", "true");
        defaultProperties.put("spring.jpa.hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        defaultProperties.put("spring.datasource.initialize", "false");
        // Prevent JPA from trying to Auto Detect the Database:
//        defaultProperties.put("spring.jpa.database", "postgresql");
        // Prevent Hibernate from Automatic Changes to the DDL Schema:
        defaultProperties.put("spring.jpa.hibernate.ddl-auto", "none");
        return defaultProperties;
    }
}