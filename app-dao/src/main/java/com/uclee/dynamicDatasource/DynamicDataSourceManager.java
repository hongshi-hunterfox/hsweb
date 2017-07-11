package com.uclee.dynamicDatasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.uclee.fundation.data.mybatis.model.AdminDataSource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;
import java.util.*;
/**
 * 初始化创建数据源
 * @author zhour
 *
 */
public class DynamicDataSourceManager {
	static Logger  logger = LoggerFactory.getLogger(DynamicDataSourceManager.class);

    //
    private static Map<Integer, ComboPooledDataSource> dataSourcePoolMap = new HashMap<Integer, ComboPooledDataSource>();
                                                    
    /**
     * 初始化加载创建数据源连接池
     */
    public static void init() {
        logger.info("-------------->开始初始化加载创建动态数据源...");
        //获取所有数据源配置信息
        List<AdminDataSource> dataSourceList = new ArrayList<>();
        AdminDataSource dd=new AdminDataSource()
        		.setId(1)
        		.setDriverClassName("com.mysql.jdbc.Driver")
        		.setUrl("jdbc:mysql://120.25.193.220:3306/fuliduobao_test?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull")
        		.setUsername("duobao")
        		.setPassword("nicaicai")
        		.setSourceStatus(1);
        AdminDataSource ff=new AdminDataSource()
        		.setId(2)
        		.setDriverClassName("net.sourceforge.jtds.jdbc.Driver")
        		.setUrl("jdbc:jtds:sqlserver://112.74.108.24:9630/masterdb")
        		.setUsername("sa")
        		.setPassword("to119,0002")
        		.setSourceStatus(1);
        dataSourceList.add(dd);
        dataSourceList.add(ff);
        
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
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(adminDataSource.getDriverClassName());
            comboPooledDataSource.setJdbcUrl(adminDataSource.getUrl());
            comboPooledDataSource.setUser(adminDataSource.getUsername());
            comboPooledDataSource.setPassword(adminDataSource.getPassword());
            //
            comboPooledDataSource.setInitialPoolSize(1);
            comboPooledDataSource.setMinPoolSize(0);
            comboPooledDataSource.setMaxPoolSize(5);
            comboPooledDataSource.setAcquireIncrement(2);
            comboPooledDataSource.setMaxIdleTime(10);
            comboPooledDataSource.setMaxStatements(0);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSourcePoolMap.put(adminDataSource.getId(), comboPooledDataSource);
    }
                                                    
    /**
     * 获取数据源
     * @param sourceId
     * @return
     */
    public static JdbcTemplate getDataSourcePoolBySourceID(int source_id) throws Exception {
        //
        ComboPooledDataSource comboPooledDataSource = dataSourcePoolMap.get(source_id);
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
        JdbcTemplate jdbcTempleDynamic = new JdbcTemplate(comboPooledDataSource);
        //
        return jdbcTempleDynamic;
    }
                                                    
    /**
     * 关闭所有数据源连接池
     */
    public static void close() {
        Set<Integer> key = dataSourcePoolMap.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();) {
            ComboPooledDataSource comboPooledDataSource = dataSourcePoolMap.get(it.next());
            try {
                comboPooledDataSource.close();
            } catch (Exception e) {
                logger.error("关闭连接池异常：comboPooledDataSource="+ToStringBuilder.reflectionToString(comboPooledDataSource));
                e.printStackTrace();
            }
        }
    }
                                                    
}
