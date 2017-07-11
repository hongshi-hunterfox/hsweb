package com.uclee.datasource.service;

import com.uclee.fundation.data.mybatis.model.DataSourceInfo;

import java.util.List;

public interface DataSourceInfoServiceI {

	List<DataSourceInfo> getAllDataSourceInfo();


	DataSourceInfo getDataSourceInfoById(Integer id);

	Boolean addDataSourceInfo(DataSourceInfo dataSourceInfo);

	Boolean updateDataSourceInfo(DataSourceInfo dataSourceInfo);


	DataSourceInfo getDataSourceInfoByCode(String mCode);
}
