package com.icat.quest.common.vo;

import java.util.List;

public class UserHasPermissionBatchVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userHasPermissionId;
	private List<Integer> permissionIdList;
	private Integer examId;
	private Integer userId;
	private String userType;
	private Boolean active;
	
	
	 
	public UserHasPermissionBatchVo() {
	}


	public UserHasPermissionBatchVo(Integer userHasPermissionId, List<Integer> permissionIdList, Integer examId,
			Integer userId, String userType, Boolean active) {
		super();
		this.userHasPermissionId = userHasPermissionId;
		this.permissionIdList = permissionIdList;
		this.examId = examId;
		this.userId = userId;
		this.userType = userType;
		this.active = active;
	}





	public Integer getUserHasPermissionId() {
		return userHasPermissionId;
	}



	public void setUserHasPermissionId(Integer userHasPermissionId) {
		this.userHasPermissionId = userHasPermissionId;
	}



	public List<Integer> getPermissionIdList() {
		return permissionIdList;
	}



	public void setPermissionIdList(List<Integer> permissionIdList) {
		this.permissionIdList = permissionIdList;
	}



	public Integer getExamId() {
		return examId;
	}



	public void setExamId(Integer examId) {
		this.examId = examId;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}




	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "UserHasPermissionBatchVo [userHasPermissionId=" + userHasPermissionId + ", permissionIdList="
				+ permissionIdList + ", examId=" + examId + ", userId=" + userId + ", userType=" + userType
				+ ", active=" + active + "]";
	}


		
}
