package com.uclee.interceptor;

import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import joptsimple.internal.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by super13 on 5/14/17.
 */
public class MerChantCodeInterceptor implements HandlerInterceptor {

    @Autowired
    private DataSourceFacade dataSource;

    private static final Logger logger = Logger.getLogger(MerChantCodeInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String merchantCode=httpServletRequest.getParameter("merchantCode");
//        logger.info("Request Url:"+httpServletRequest.getRequestURI());
        logger.info("merchantCode:"+merchantCode);
        HttpSession session=httpServletRequest.getSession();
        String mCode=(String)session.getAttribute(GlobalSessionConstant.MERCHANT_CODE);

        if(Strings.isNullOrEmpty(merchantCode)) {
            if(Strings.isNullOrEmpty(mCode)) {
                httpServletResponse.sendError(406);//都没有返回错误
                return false;
            }else{
                dataSource.switchDataSource(mCode);//切换session里的merchantCode
            }
        }else{// 可能要切换了
            if(!merchantCode.equals(mCode)){
                session.setAttribute(GlobalSessionConstant.USER_ID,null);//切换数据源清空所有用户相关session
            }
            session.setAttribute(GlobalSessionConstant.MERCHANT_CODE,merchantCode);
            dataSource.switchDataSource(merchantCode);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
