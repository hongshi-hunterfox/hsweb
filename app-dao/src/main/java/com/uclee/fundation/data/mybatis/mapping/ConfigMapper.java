package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.fundation.data.web.dto.ConfigPost;

public interface ConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
    
    List<Config> selectAll();

	int updateByTag(@Param("tag") String tag, @Param("value") String value);

	Config getByTag(String tag);

	List<Config> getWeixinConfig();

	List<Config> getAlipayConfig();

	List<Config> getSMSConfig();

    List<Config> getWeixinCertificateConfig();


}