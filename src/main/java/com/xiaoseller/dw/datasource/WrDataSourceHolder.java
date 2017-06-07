package com.xiaoseller.dw.datasource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;

public class WrDataSourceHolder {
	private ThreadLocal<String> dsHolder = new ThreadLocal<String>();
	private AtomicInteger index = new AtomicInteger(0);
	@Autowired
	private WrDataSourceProperties wrConfiguration;

	private void setDataSource(String dataSource) {
		dsHolder.set(dataSource);
	}

	public void setMaster() {
		setDataSource(wrConfiguration.getMasterName());
	}

	public void setSlave() {
		List<String> slaves = wrConfiguration.getSlaves();
		if (slaves.size() == 1) {
			setDataSource(slaves.get(0));
			return;
		}
		if (index.get() >= slaves.size()) {
			index.set(0);
		}
		setDataSource(slaves.get(index.get() % slaves.size()));
		index.addAndGet(1);
	}

	public String getDataSource() {
		return dsHolder.get() == null ? wrConfiguration.getMasterName() : dsHolder.get();
//		return Optional.ofNullable(dsHolder.get()).orElse(wrConfiguration.getMasterName());
	}

	public void clearDataSource() {
		dsHolder.remove();
	}
}
