package com.uclee.dynamicDatasource;

/**
 * @author heyaopeng
 * @descrice 多个登录用户可能需要同时切换数据源，所以这里需要写一个线程安全的ThreadLocal
 * @more 用户切换数据源只要在程序中使用 DBContextHolder.setDBType("1") 即可完成数据源切换
 */
public class DBContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }
    public static String getDBType() {
        return (String) contextHolder.get();
    }
    public static void clearDBType() {
        contextHolder.remove();
    }
}