package com.backend.model;

import com.uclee.fundation.data.mybatis.model.Permission;
import com.uclee.fundation.data.mybatis.model.Role;

import java.util.List;

public class RoleForm extends Role{
	List<Permission> permission;
	
	private Integer userId;
	
	private String permissions[];
	
	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions[]) {
		this.permissions = permissions;
	}
}
