package com.uclee.dynamicDatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.uclee.fundation.data.mybatis.model.DataSourceInfo;
import joptsimple.internal.Strings;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by super13 on 5/10/17.
 */
public class DataSourceFacade implements DataSource {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DataSourceFacade.class);
    private ThreadLocal<String> local = new ThreadLocal<String>();

    private Map<Object, Object> targetDataSources;



    @Autowired
    private DruidDataSource druidDataSource;

    @Autowired
    private ApplicationContext applicationContext;



    private DataSource dataSource = null;

    public DataSourceFacade(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public DataSourceFacade() {
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

    public DataSource getDataSource() throws SQLException {
        String dataSourceName = this.local.get();
        if(dataSourceName==null){
            return getDataSource("druidDataSource");
        }
        //logger.info("dataSourceName:"+dataSourceName);
        return getDataSource(dataSourceName);
    }
    public String getDataSourceStr(){
        String dataSourceName = this.local.get();
		return dataSourceName;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void  close(){

    }

    public DataSource getDataSource(String dataSourceName)throws SQLException{
        DataSource druidDataSource1=null;
        try {
            druidDataSource1 = (DataSource) applicationContext.getBean(dataSourceBeanName(dataSourceName));
        }catch(NoSuchBeanDefinitionException e ) {
                DruidPooledConnection conn = druidDataSource.getConnection();
                Statement stmt = conn.createStatement();
                String sql="select * from web_datasource_info where merchant_code='%s'";
                sql=String.format(sql,dataSourceName);
                ResultSet result = stmt.executeQuery(sql);
                while(result.next()){
                    DataSourceInfo info=new DataSourceInfo();

                    info.setDriverClassName(result.getString("driver_class_name"));
                    info.setUsername(result.getString("username"));
                    info.setPassword(result.getString("password"));
                    info.setUrl(result.getString("url"));
                    info.setMerchantCode(result.getString("merchant_code"));
                    conn.abandond();
                    return getDataSource(info);
                }

            druidDataSource1=druidDataSource;

        }
        return druidDataSource1;
    }
    public DataSource getDataSource(DataSourceInfo dataSourceInfo) {

        DataSource druidDataSource1=null;

        try {
            druidDataSource1 = (DataSource) applicationContext.getBean(dataSourceBeanName(dataSourceInfo.getMerchantCode()));
        }catch(NoSuchBeanDefinitionException e ) {
            logger.info("no bean");
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DruidDataSource.class);
            beanDefinitionBuilder.addPropertyValue("driverClassName", dataSourceInfo.getDriverClassName());
            beanDefinitionBuilder.addPropertyValue("url", dataSourceInfo.getUrl());
            beanDefinitionBuilder.addPropertyValue("username", dataSourceInfo.getUsername());
            beanDefinitionBuilder.addPropertyValue("password", dataSourceInfo.getPassword());
            beanDefinitionBuilder.addPropertyValue("initialSize",0);
            beanDefinitionBuilder.addPropertyValue("maxActive",20);
            beanDefinitionBuilder.addPropertyValue("minIdle",1);
            beanDefinitionBuilder.addPropertyValue("maxWait",60000);
            beanDefinitionBuilder.addPropertyValue("validationQuery","select 1");
            beanDefinitionBuilder.addPropertyValue("testOnBorrow",false);
            beanDefinitionBuilder.addPropertyValue("testOnReturn",false);
            beanDefinitionBuilder.addPropertyValue("testWhileIdle",true);
            beanDefinitionBuilder.addPropertyValue("timeBetweenEvictionRunsMillis",60000);
            beanDefinitionBuilder.addPropertyValue("minEvictableIdleTimeMillis",25200000);
            beanDefinitionBuilder.addPropertyValue("removeAbandoned",true);
            beanDefinitionBuilder.addPropertyValue("removeAbandonedTimeout",1800);
            beanDefinitionBuilder.addPropertyValue("logAbandoned",true);
            beanDefinitionBuilder.addPropertyValue("filters","mergeStat");


            beanFactory.registerBeanDefinition(dataSourceBeanName(dataSourceInfo.getMerchantCode()), beanDefinitionBuilder.getBeanDefinition());
            druidDataSource1=(DataSource) applicationContext.getBean(dataSourceBeanName(dataSourceInfo.getMerchantCode()));
            //beanFactory.registerBeanDefinition("druidDataSource1", beanDefinitionBuilder.getBeanDefinition());
            //druidDataSource1=(DataSource) applicationContext.getBean("druidDataSource1");

            if(druidDataSource1==null){//如果还错证明错误配置返回默认数据源
                return druidDataSource;
            }
        }
        //logger.info("使用切换后的数据源:"+dataSourceInfo.getMerchantCode());
        System.out.println("使用切换后的数据源:"+dataSourceInfo.getMerchantCode());
        return druidDataSource1;
    }

    public int switchDataSource(String dataSourceName){
        if(Strings.isNullOrEmpty(dataSourceName))
            return -1;

        // 切换数据源
        this.local.set(dataSourceName);

        //logger.info("切换后的数据源名字"+this.local.get());

        return 0;
    }

    private String dataSourceBeanName(String merchantCode){
        String ret="dataSource"+merchantCode.trim().toLowerCase();
        return ret;
    }


}