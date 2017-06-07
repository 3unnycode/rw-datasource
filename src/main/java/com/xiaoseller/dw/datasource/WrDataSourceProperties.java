package com.xiaoseller.dw.datasource;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.wr.datasource")
public class WrDataSourceProperties {

	private List<String> slaves;
	private String masterName;

	/**
	 * @return the slaves
	 */
	public List<String> getSlaves() {
		return slaves;
	}

	/**
	 * @param slaves
	 *            the slaves to set
	 */
	public void setSlaves(List<String> slaves) {
		this.slaves = slaves;
	}

	/**
	 * @return the masterName
	 */
	public String getMasterName() {
		return masterName;
	}

	/**
	 * @param masterName
	 *            the masterName to set
	 */
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
}
