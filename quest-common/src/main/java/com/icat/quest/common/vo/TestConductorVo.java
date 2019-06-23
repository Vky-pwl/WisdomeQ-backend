package com.icat.quest.common.vo;

import java.util.List;

public class TestConductorVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer testConductorId;
	private CollegeVo collegeVo;
	private String firstName;
	private String lastName;
	private String contactEmail;
	private String password;
	private UserType adminType;
	private Boolean active;
	private Integer parentTestConductorId;
	private String token;
	private List<PermissionVo> permissionVos;

	
	 
	public TestConductorVo() {
	}

	public TestConductorVo(Integer testConductorId) {
		super();
		this.testConductorId = testConductorId;
	}


	
	public TestConductorVo(Integer testConductorId, CollegeVo collegeVo, String firstName, String lastName,
			String contactEmail, String password, UserType adminType, Boolean active, Integer parentTestConductorId,
			String token, List<PermissionVo> permissionVos) {
		super();
		this.testConductorId = testConductorId;
		this.collegeVo = collegeVo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactEmail = contactEmail;
		this.password = password;
		this.adminType = adminType;
		this.active = active;
		this.parentTestConductorId = parentTestConductorId;
		this.token = token;
		this.permissionVos = permissionVos;
	}

	public Integer getTestConductorId() {
		return testConductorId;
	}





	public void setTestConductorId(Integer testConductorId) {
		this.testConductorId = testConductorId;
	}





	public CollegeVo getCollegeVo() {
		return collegeVo;
	}



	public void setCollegeVo(CollegeVo collegeVo) {
		this.collegeVo = collegeVo;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getContactEmail() {
		return contactEmail;
	}



	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}




	public UserType getAdminType() {
		return adminType;
	}

	public void setAdminType(UserType adminType) {
		this.adminType = adminType;
	}

	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public Integer getParentTestConductorId() {
		return parentTestConductorId;
	}

	public void setParentTestConductorId(Integer parentTestConductorId) {
		this.parentTestConductorId = parentTestConductorId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

	public List<PermissionVo> getPermissionVos() {
		return permissionVos;
	}

	public void setPermissionVos(List<PermissionVo> permissionVos) {
		this.permissionVos = permissionVos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TestConductorVo [testConductorId=" + testConductorId + ", collegeVo=" + collegeVo + ", firstName="
				+ firstName + ", lastName=" + lastName + ", contactEmail=" + contactEmail + ", password=" + password
				+ ", adminType=" + adminType + ", active=" + active + ", parentTestConductorId=" + parentTestConductorId
				+ ", token=" + token + ", permissionVos=" + permissionVos + "]";
	}

	
}
