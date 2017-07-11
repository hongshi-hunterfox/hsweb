package com.uclee.web.backend.vo;

/**
 * Created by super13 on 5/31/17.
 */
public class UserVo {
    private Integer userId;

    private String phone;

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
