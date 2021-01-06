package com.bytesvc.config;

import javax.sql.DataSource;

import org.apache.curator.retry.ExponentialBackoffRetry;
import org.bytesoft.bytetcc.supports.dubbo.config.DubboSecondaryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * 按请求粒度负载均衡(使用MongoDB存储事务日志):需引入DubboSupportConfiguration; <br />
 * 按事务粒度负载均衡(使用文件系统存储事务日志):需引入DubboSecondaryConfiguration;
 */
@Import(DubboSecondaryConfiguration.class)
@Configuration
@DubboComponentScan(basePackages = { "com.bytesvc.service.impl", "com.bytesvc.service.confirm", "com.bytesvc.service.cancel" })
@EnableDubbo(scanBasePackages = { "com.bytesvc.service", "com.bytesvc.config" })
public class ConsumerConfig {

	/**
	 * 使用'按请求粒度负载均衡'策略时需要添加该配置.
	 */
	// @Bean // 使用文件存储事务日志时, 不需要配置zookeeper
	public org.apache.curator.framework.CuratorFramework curatorFramework() {
		org.apache.curator.framework.CuratorFramework curatorFramework = //
				org.apache.curator.framework.CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
						.sessionTimeoutMs(1000 * 6).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		curatorFramework.start();
		return curatorFramework;
	}

	@Bean("mysql1")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mysql2")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public JdbcTemplate jdbcTemplate1(@Autowired @Qualifier("mysql1") DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}

	@Bean
	public JdbcTemplate jdbcTemplate2(@Autowired @Qualifier("mysql2") DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}

}
