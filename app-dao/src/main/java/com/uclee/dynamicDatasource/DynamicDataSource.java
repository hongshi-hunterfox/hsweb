package com.uclee.dynamicDatasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
 
/**
 * @author heyaopeng
 * @describe 实现动态数据源切换逻辑
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private Logger log = Logger.getLogger(this.getClass());
    private Map<Object, Object> _targetDataSources;
 
    /**
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     * @describe 数据源为空或者为0时，自动切换至默认数据源，即在配置文件中定义的dataSource数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DBContextHolder.getDBType();
        if (dataSourceName == null) {
            dataSourceName = "dataSource";
        } else {
            this.selectDataSource(Integer.valueOf(dataSourceName));
            if (dataSourceName.equals("0"))
                dataSourceName = "dataSource";
        }
        log.debug("--------> use datasource " + dataSourceName);
        return dataSourceName;
    }
 
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this._targetDataSources = targetDataSources;
        super.setTargetDataSources(this._targetDataSources);
        afterPropertiesSet();
    }
 
    public void addTargetDataSource(String key, BasicDataSource dataSource) {
        this._targetDataSources.put(key, dataSource);
        this.setTargetDataSources(this._targetDataSources);
    }
 
    public BasicDataSource createDataSource(String driverClassName, String url,
            String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(true);
        return dataSource;
    }
 
    /**
     * @param serverId
     * @describe 数据源存在时不做处理，不存在时创建新的数据源链接，并将新数据链接添加至缓存
     */
    public void selectDataSource(Integer serverId) {
        Object sid = DBContextHolder.getDBType();
        if ("0".equals(serverId + "")) {
            DBContextHolder.setDBType("0");
            return;
        }
        Object obj = this._targetDataSources.get(serverId);
        if (obj != null && sid.equals(serverId + "")) {
            return;
        } else {
            BasicDataSource dataSource = this.getDataSource(serverId);
            if (null != dataSource)
                this.setDataSource(serverId, dataSource);
        }
    }
 
    /**
     * @describe 查询serverId对应的数据源记录
     * @param serverId
     *  @return
     */
    public BasicDataSource getDataSource(Integer serverId) {
        this.selectDataSource(0);
        this.determineCurrentLookupKey();
        Connection conn = null;
        HashMap<String, Object> map = null;
        try {
            conn = this.getConnection();
            PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM db_datasource WHERE DBS_ID = ?");
            ps.setInt(1, serverId);
            ResultSet rs = ps.executeQuery();
            map = new HashMap<String, Object>();
            if (rs.next()) {
                map.put("id", rs.getInt("id"));
                map.put("driver_class_name", rs
                        .getString("driver_class_name"));
                map.put("url", rs.getString("url"));
                map.put("username", rs.getString("username"));
                map.put("password", rs.getString("password"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
        if (null != map) {
            String driverClassName = map.get("driver_class_name").toString();
            String url = map.get("url").toString();
            String userName = map.get("username").toString();
            String password = map.get("password").toString();
            BasicDataSource dataSource = this.createDataSource(driverClassName,
                    url, userName, password);
            return dataSource;
        }
        return null;
    }
 
    /**
     * @param serverId
     * @param dataSource
     */
    public void setDataSource(Integer serverId, BasicDataSource dataSource) {
        this.addTargetDataSource(serverId + "", dataSource);
        DBContextHolder.setDBType(serverId + "");
    }
 
}
