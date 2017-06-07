package com.xiaoseller.dw.datasource;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class WrRoutingDataSource extends AbstractRoutingDataSource {

	@Resource
	private WrDataSourceHolder wrDataSourceHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
	 * determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return wrDataSourceHolder.getDataSource();
	}

}
