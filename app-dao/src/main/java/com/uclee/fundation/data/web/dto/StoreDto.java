package com.uclee.fundation.data.web.dto;/**
 * Created by heyaopeng on 2017/9/3.
 */

import com.uclee.fundation.data.mybatis.model.NapaStore;

/**
 * UCSHOP 版权所有 2017-2018,并保留所有权利。
 * <p>
 * ----------------------------------------------------------------------------
 * --------- 提示：在未取得优势力科技商业授权之前，您不能将本软件应用于商业用途，否则优势力科技将保留追究的权力。
 * --------
 * ----------------------------------------------------------------------------
 * <p>
 * 官方网站：http://www.uclee.com
 * <p>
 * Created by heyaopeng on 2017/9/3.
 */
public class StoreDto extends NapaStore {
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
