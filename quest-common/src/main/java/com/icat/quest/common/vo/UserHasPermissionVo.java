package com.icat.quest.common.vo;

public class UserHasPermissionVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userHasPermissionId;
	private Integer permissionId;
	private String permissionName;
	private Integer examId;
	private Integer userId;
	private UserType userType;
	private Boolean active;
	
	
	 
	public UserHasPermissionVo() {
	}





	public UserHasPermissionVo(Integer userHasPermissionId, Integer permissionId, String permissionName, Integer examId,
			Integer userId, UserType userType, Boolean active) {
		super();
		this.userHasPermissionId = userHasPermissionId;
		this.permissionId = permissionId;
		this.permissionName = permissionName;
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




	public Integer getPermissionId() {
		return permissionId;
	}




	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
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




	public UserType getUserType() {
		return userType;
	}



	public void setUserType(UserType userType) {
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





	public String getPermissionName() {
		return permissionName;
	}





	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	
		
}
