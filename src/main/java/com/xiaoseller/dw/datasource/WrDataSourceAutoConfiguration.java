package com.xiaoseller.dw.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(WrDataSourceProperties.class)
public class WrDataSourceAutoConfiguration {

	private final static Logger LOGGER = LoggerFactory.getLogger(WrDataSourceAutoConfiguration.class);
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private WrDataSourceProperties wrConfiguration;

	@Bean
	@ConditionalOnMissingBean
	public WrRoutingDataSource wrRoutingDataSource() {
		WrRoutingDataSource datasouce = new WrRoutingDataSource();
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		String masterName = wrConfiguration.getMasterName();
		Assert.hasText(masterName, "masterName cannot be null");
		targetDataSources.put(masterName, applicationContext.getBean(masterName));
		List<String> slaves = wrConfiguration.getSlaves();
		Assert.notEmpty(slaves, "slaves cannot be empty");
		for (String slave : slaves) {
			targetDataSources.put(slave, applicationContext.getBean(slave));
		}
		datasouce.setTargetDataSources(targetDataSources);
		LOGGER.info("init wrDatasource, masterName:{}, slaves:{}", masterName, slaves);
		return datasouce;
	}

	@Bean
	public WrDataSourceHolder dataSourceHolder() {
		return new WrDataSourceHolder();
	}

	@Bean
	public WrDataSourceMethodAspect wrDataSourceMethodAspect() {
		return new WrDataSourceMethodAspect();
	}
}
