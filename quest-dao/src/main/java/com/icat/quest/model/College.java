package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "College")
public class College implements java.io.Serializable {

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
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 
	public College() {
	}


	


	public College(String collegeName, String collegeCode, String addressLine1, String addressLine2, String city,
			String state, String country, String pinCode, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.collegeName = collegeName;
		this.collegeCode = collegeCode;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}








	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "collegeId", unique = true, nullable = false)
	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	

	@Column(name = "collegeName",nullable=true, length =100)
	public String getCollegeName() {
		return collegeName;
	}




	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}




	@Column(name = "collegeCode",  length = 45)
	public String getCollegeCode() {
		return collegeCode;
	}




	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}



	@Column(name = "addressLine1",nullable = false,length=100)
	public String getAddressLine1() {
		return addressLine1;
	}




	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}



	@Column(name = "addressLine2", length =100)
	public String getAddressLine2() {
		return addressLine2;
	}




	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}




	@Column(name = "city",nullable = false, length =45)
	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	@Column(name = "state",nullable = false, length =45)
	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	@Column(name = "country",nullable = false, length =45)
	public String getCountry() {
		return country;
	}




	public void setCountry(String country) {
		this.country = country;
	}




	@Column(name = "pinCode",nullable = false, length =10)
	public String getPinCode() {
		return pinCode;
	}




	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}




	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return created;
	}




	public void setCreated(Date created) {
		this.created = created;
	}




	@Column(name = "createdBy", nullable = false, length = 20)
	public Long getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModified", nullable = false, length = 19)
	public Date getLastModified() {
		return lastModified;
	}




	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}




	@Column(name = "lastModifiedBy", nullable = false, length = 20)
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}




	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	
		
}
