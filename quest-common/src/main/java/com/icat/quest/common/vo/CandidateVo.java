package com.icat.quest.common.vo;

import java.util.List;

public class CandidateVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private CollegeVo collegeVo;
	private SpecializationVo specializationVo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private Long dateOfBirth;
	private String contactEmail;
	private String password;
	private String contactNumber;
	private String adhaarNumber;
	private String tenthPercentage;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private Boolean active;
	private String token;
	private List<PermissionVo> permissionVos;
	private Integer examId;
	private String testConductorLicenseCode;
	
	
	 
	public CandidateVo() {
	}

	public CandidateVo(Integer userId) {
		super();
		this.userId = userId;
	}


	public CandidateVo(Integer userId, CollegeVo collegeVo, SpecializationVo specializationVo, String firstName,
			String middleName, String lastName, String gender, Long dateOfBirth, String contactEmail,
			String password, String contactNumber, String adhaarNumber, String tenthPercentage, String addressLine1,
			String addressLine2, String city, String state, String country, String pinCode, Boolean active,
			String token, List<PermissionVo> permissionVos, Integer examId) {
		super();
		this.userId = userId;
		this.collegeVo = collegeVo;
		this.specializationVo = specializationVo;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.contactEmail = contactEmail;
		this.password = password;
		this.contactNumber = contactNumber;
		this.adhaarNumber = adhaarNumber;
		this.tenthPercentage = tenthPercentage;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.active = active;
		this.token = token;
		this.permissionVos = permissionVos;
		this.examId = examId;
	}

	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}




	public Long getDateOfBirth() {
		return dateOfBirth;
	}





	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}





	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getMiddleName() {
		return middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getGender() {
		return gender.trim();
	}



	public void setGender(String gender) {
		this.gender = gender;
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



	public String getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	public String getAdhaarNumber() {
		return adhaarNumber;
	}



	public void setAdhaarNumber(String adhaarNumber) {
		this.adhaarNumber = adhaarNumber;
	}



	public String getTenthPercentage() {
		return tenthPercentage;
	}



	public void setTenthPercentage(String tenthPercentage) {
		this.tenthPercentage = tenthPercentage;
	}



	public String getAddressLine1() {
		return addressLine1;
	}



	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}



	public String getAddressLine2() {
		return addressLine2;
	}



	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPinCode() {
		return pinCode;
	}



	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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








	public CollegeVo getCollegeVo() {
		return collegeVo;
	}








	public void setCollegeVo(CollegeVo collegeVo) {
		this.collegeVo = collegeVo;
	}








	public SpecializationVo getSpecializationVo() {
		return specializationVo;
	}


	public List<PermissionVo> getPermissionVos() {
		return permissionVos;
	}

	public void setPermissionVos(List<PermissionVo> permissionVos) {
		this.permissionVos = permissionVos;
	}

	public void setSpecializationVo(SpecializationVo specializationVo) {
		this.specializationVo = specializationVo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	
	

	public String getTestConductorLicenseCode() {
		return testConductorLicenseCode;
	}

	public void setTestConductorLicenseCode(String testConductorLicenseCode) {
		this.testConductorLicenseCode = testConductorLicenseCode;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateVo other = (CandidateVo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CandidateVo [userId=" + userId + ", collegeVo=" + collegeVo + ", specializationVo=" + specializationVo
				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", gender="
				+ gender + ", dateOfBirth=" + dateOfBirth + ", contactEmail=" + contactEmail + ", password=" + password
				+ ", contactNumber=" + contactNumber + ", adhaarNumber=" + adhaarNumber + ", tenthPercentage="
				+ tenthPercentage + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", state=" + state + ", country=" + country + ", pinCode=" + pinCode + ", active=" + active
				+ ", token=" + token + ", permissionVos=" + permissionVos + ", examId=" + examId + "]";
	}

	
}
