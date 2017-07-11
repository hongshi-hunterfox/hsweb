package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class UserInvitedLink {

    private Integer userId;

    private Integer invitedId;
    
    private Date inviteTime;
    
    private String timeStr;
    
    private UserProfile userProfile;

    public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Date getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(Date inviteTime) {
		this.inviteTime = inviteTime;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(Integer invitedId) {
        this.invitedId = invitedId;
    }
}