package com.uclee.dynamicDatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.uclee.fundation.data.mybatis.model.AdminDataSource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
/**
 * 初始化创建数据源
 * @author zhour
 *
 */
public class DynamicDataSourceManagerHeyp {
	static Logger  logger = LoggerFactory.getLogger(DynamicDataSourceManagerHeyp.class);

    //
    private static Map<Integer, DruidDataSource> dataSourcePoolMap = new HashMap<Integer, DruidDataSource>();
                                                    
    /**
     * 初始化加载创建数据源连接池
     */
    public static void init() {
        logger.info("-------------->开始初始化加载创建动态数据源...");
        //获取所有数据源配置信息
        
        DruidDataSource mDruidDataSource = new DruidDataSource();
    	mDruidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	mDruidDataSource.setUrl("jdbc:mysql://120.25.193.220:3306/fuliduobao_test?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull");
    	mDruidDataSource.setUsername("duobao");
    	mDruidDataSource.setPassword("nicaicai");
    	mDruidDataSource.setInitialSize(0);
    	mDruidDataSource.setMaxActive(20);
    	mDruidDataSource.setMaxWait(60000);
    	mDruidDataSource.setMinIdle(1);
    	mDruidDataSource.setValidationQuery("SELECT 1");
    	mDruidDataSource.setTestOnReturn(false);
    	mDruidDataSource.setTestOnBorrow(false);
    	mDruidDataSource.setTestWhileIdle(true);
    	JdbcTemplate jdbcTemple = new JdbcTemplate(mDruidDataSource);
        
        List<Map<String,Object>> dataSourceList1 = jdbcTemple.queryForList("select * from db_datasource");
        List<AdminDataSource> dataSourceList = new ArrayList<>();
        for(Map<String,Object> entry : dataSourceList1) {
        	//
        	AdminDataSource tmp = new AdminDataSource()
        			.setId(((Long)entry.get("id")).intValue())
        			.setDriverClassName((String)entry.get("driver_class_name"))
        			.setPassword((String)entry.get("password"))
        			.setUrl((String)entry.get("url"))
        			.setUsername((String)entry.get("username"));
        	dataSourceList.add(tmp);
        }
        for(AdminDataSource adminDataSource : dataSourceList) {
            //
            createDataSourcePool(adminDataSource);
        }
        logger.info("-------------->初始化加载创建动态数据源完毕，加载数："+dataSourceList.size());
    }
                                                    
    /**
     * 创建数据源连接池
     * @param adminDataSource
     */
    public static void createDataSourcePool(AdminDataSource adminDataSource) {
    	DruidDataSource druidDataSource = new DruidDataSource();
    	druidDataSource.setDriverClassName(adminDataSource.getDriverClassName());
    	druidDataSource.setUrl(adminDataSource.getUrl());
    	druidDataSource.setUsername(adminDataSource.getUsername());
    	druidDataSource.setPassword(adminDataSource.getPassword());
    	druidDataSource.setInitialSize(0);
    	druidDataSource.setMaxActive(20);
    	druidDataSource.setMaxWait(60000);
    	druidDataSource.setMinIdle(1);
    	druidDataSource.setValidationQuery("SELECT 1");
    	druidDataSource.setTestOnReturn(false);
    	druidDataSource.setTestOnBorrow(false);
    	druidDataSource.setTestWhileIdle(true);
        dataSourcePoolMap.put(adminDataSource.getId(), druidDataSource);
    }
                                                    
    /**
     * 获取数据源
     * @param sourceId
     * @return
     */
    public static JdbcTemplate getDataSourcePoolBySourceID(int source_id) throws Exception {
        //
    	DruidDataSource druidDataSource = dataSourcePoolMap.get(source_id);
/*        if(comboPooledDataSource == null) {
            logger.info(String.format("未找到[SourceID=%d]对应的数据源，则从数据库重新获取...", source_id));
            //未获取到相应的数据源，则从数据库重新获取，来创建新的数据源连接池
            AdminDataSource adminDataSource = dynamicDataSourceDao.getAdminDataSourceById(source_id);
            if(adminDataSource == null) {
                logger.info(String.format("从数据库重新获取，未找到[SourceID=%d]对应的数据源", source_id));
                throw new Exception("未获取到匹配的动态数据源[ID="+source_id+"]");
            } else if(adminDataSource.getSource_state() != 1) {
                logger.info(String.format("[SourceID=%d]对应的数据源状态未开启", source_id));
                throw new Exception("匹配的动态数据源状态未开启[ID="+source_id+"]");
            } else {
                //add
                createDataSourcePool(adminDataSource);
                //get
                comboPooledDataSource = this.dataSourcePoolMap.get(source_id);
            }
                                                            
        } else {
        }*/
        logger.info(String.format("已找到[SourceID=%d]对应的数据源!", source_id));
        //
        JdbcTemplate jdbcTempleDynamic = new JdbcTemplate(druidDataSource);
        //
        return jdbcTempleDynamic;
    }
                                                    
    /**
     * 关闭所有数据源连接池
     */
    public static void close() {
        Set<Integer> key = dataSourcePoolMap.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();) {
        	DruidDataSource druidDataSource = dataSourcePoolMap.get(it.next());
            try {
            	druidDataSource.close();
            } catch (Exception e) {
                logger.error("关闭连接池异常：comboPooledDataSource="+ToStringBuilder.reflectionToString(druidDataSource));
                e.printStackTrace();
            }
        }
    }
                                                    
}
