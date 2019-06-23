package com.icat.quest.common.utils;

import java.io.Serializable;

import com.icat.quest.common.vo.UserType;

public class PrincipalVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private UserType userType;
	private Integer userLoginId;
	private Integer collegeId;
	public PrincipalVo() {
		super();
	}
	
	public PrincipalVo(Integer userId, UserType userType, Integer userLoginId, Integer collegeId) {
		super();
		this.userId = userId;
		this.userType = userType;
		this.userLoginId = userLoginId;
		this.collegeId = collegeId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Integer getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(Integer userLoginId) {
		this.userLoginId = userLoginId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	
	

}
