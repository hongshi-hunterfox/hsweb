package com.uclee.datasource.service.impl;

import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.fundation.data.mybatis.mapping.DataSourceInfoMapper;
import com.uclee.fundation.data.mybatis.model.DataSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by super13 on 6/6/17.
 */
public class DataSourceInfoServiceImpl implements DataSourceInfoServiceI {
    @Autowired
    private DataSourceInfoMapper dataSourceInfoMapper;
    @Override
    public List<DataSourceInfo> getAllDataSourceInfo() {

        return dataSourceInfoMapper.selectAllDataSourceInfo();
    }

    @Override
    public DataSourceInfo getDataSourceInfoById(Integer id) {
        return dataSourceInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean addDataSourceInfo(DataSourceInfo dataSourceInfo) {
        return dataSourceInfoMapper.insertSelective(dataSourceInfo)>0;
    }

    @Override
    public Boolean updateDataSourceInfo(DataSourceInfo dataSourceInfo) {
        return dataSourceInfoMapper.updateByPrimaryKeySelective(dataSourceInfo)>0;
    }

	@Override
	public DataSourceInfo getDataSourceInfoByCode(String mCode) {
		return dataSourceInfoMapper.selectByCode(mCode);
	}
}
