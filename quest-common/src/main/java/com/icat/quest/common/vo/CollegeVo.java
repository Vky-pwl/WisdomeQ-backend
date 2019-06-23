package com.icat.quest.common.vo;

public class CollegeVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer collegeId;
	private String collegeName;
	private String collegeCode;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private Boolean active;
	
	
	 
	public CollegeVo() {
	}



	public CollegeVo(Integer collegeId) {
		super();
		this.collegeId = collegeId;
	}



	public CollegeVo(Integer collegeId, String collegeName, String collegeCode, String addressLine1, String addressLine2,
			String city, String state, String country, String pinCode, Boolean active) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.collegeCode = collegeCode;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.active = active;
	}



	public Integer getCollegeId() {
		return collegeId;
	}



	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}



	public String getCollegeName() {
		return collegeName;
	}



	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}



	public String getCollegeCode() {
		return collegeCode;
	}



	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
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



	@Override
	public String toString() {
		return "CollegeVo [collegeId=" + collegeId + ", collegeName=" + collegeName + ", collegeCode=" + collegeCode
				+ ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city + ", state="
				+ state + ", country=" + country + ", pinCode=" + pinCode + ", active=" + active + "]";
	}


	
}
