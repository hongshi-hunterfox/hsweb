package com.uclee.fundation.data.web.dto;/**
 * Created by heyaopeng on 2017/8/30.
 */

import java.util.List;

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
 * Created by heyaopeng on 2017/8/30.
 */
public class QuickNaviProductPost {

    private Integer naviId;

    private List<Integer> productIds;

    public Integer getNaviId() {
        return naviId;
    }

    public void setNaviId(Integer naviId) {
        this.naviId = naviId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
