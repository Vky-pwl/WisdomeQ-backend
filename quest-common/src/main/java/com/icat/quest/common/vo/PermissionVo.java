package com.icat.quest.common.vo;

public class PermissionVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer permissionId;
	private String permissionName;
	private Boolean active;
	
	
	 
	public PermissionVo() {
	}

	
	public PermissionVo(Integer permissionId, String permissionName, Boolean active) {
		super();
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.active = active;
	}


	public Integer getPermissionId() {
		return permissionId;
	}



	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}



	public String getPermissionName() {
		return permissionName;
	}



	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}


	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}


	@Override
	public String toString() {
		return "PermissionVo [permissionId=" + permissionId + ", permissionName=" + permissionName + ", active="
				+ active + "]";
	}


	
}
