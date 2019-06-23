package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.GenderType;



@Entity
@Table(name = "User")
public class Candidate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private College college;
	private Specialization specialization;
	private String firstName;
	private String middleName;
	private String lastName;
	private GenderType gender;
	private Date dateOfBirth;
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
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 
	public Candidate() {
	}


	
	
	public Candidate(College college, Specialization specialization, String firstName, String middleName, String lastName,
			GenderType gender, Date dateOfBirth, String contactEmail, String password, String contactNumber,
			String adhaarNumber, String tenthPercentage, String addressLine1, String addressLine2, String city,
			String state, String country, String pinCode, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.college = college;
		this.specialization = specialization;
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
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collegeId")	
	public College getCollege() {
		return college;
	}



	public void setCollege(College college) {
		this.college = college;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "specializationId")	
	public Specialization getSpecialization() {
		return specialization;
	}



	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}


	@Column(name = "firstName", nullable = false, length=45)
	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	@Column(name = "middleName", length=45)
	public String getMiddleName() {
		return middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}



	@Column(name = "lastName", nullable = false, length=45)
	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	 @Enumerated(EnumType.STRING)
	 @Column(columnDefinition = "gender", nullable = false)
	public GenderType getGender() {
		return gender;
	}



	public void setGender(GenderType gender) {
		this.gender = gender;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateOfBirth",  length = 19)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	@Column(name = "contactEmail", length=100)
	public String getContactEmail() {
		return contactEmail;
	}



	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}



	@Column(name = "password",  length=45)
	public String getPassword() {
		return password;
	}









	public void setPassword(String password) {
		this.password = password;
	}









	@Column(name = "contactNumber",  length=15)
	public String getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	@Column(name = "adhaarNumber",  length=30)
	public String getAdhaarNumber() {
		return adhaarNumber;
	}



	public void setAdhaarNumber(String adhaarNumber) {
		this.adhaarNumber = adhaarNumber;
	}



	@Column(name = "tenthPercentage",  length=10)
	public String getTenthPercentage() {
		return tenthPercentage;
	}



	public void setTenthPercentage(String tenthPercentage) {
		this.tenthPercentage = tenthPercentage;
	}



	@Column(name = "addressLine1",length=100)
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




	@Column(name = "city", length =45)
	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	@Column(name = "state", length =45)
	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	@Column(name = "country", length =45)
	public String getCountry() {
		return country;
	}




	public void setCountry(String country) {
		this.country = country;
	}




	@Column(name = "pinCode",length =10)
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
