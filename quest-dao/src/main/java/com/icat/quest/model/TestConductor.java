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

import com.icat.quest.common.vo.UserType;



@Entity
@Table(name = "TestConductor")
public class TestConductor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer testConductorId;
	private College college;
	private String firstName;
	private String lastName;
	private String contactEmail;
	private String password;
	private UserType adminType ;
	private Integer parentTestConductorId;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 
	public TestConductor() {
	}



	public TestConductor(College college, String firstName, String lastName, String contactEmail, String password,
			UserType adminType, Integer parentTestConductorId, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.college = college;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactEmail = contactEmail;
		this.password = password;
		this.adminType = adminType;
		this.parentTestConductorId = parentTestConductorId;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testConductorId", unique = true, nullable = false)
	public Integer getTestConductorId() {
		return testConductorId;
	}



	public void setTestConductorId(Integer testConductorId) {
		this.testConductorId = testConductorId;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collegeId")	
	public College getCollege() {
		return college;
	}



	public void setCollege(College college) {
		this.college = college;
	}

	@Column(name = "firstName", nullable = false, length=45)
	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name = "lastName", nullable = false, length=45)
	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "contactEmail", nullable = false, length=100)
	public String getContactEmail() {
		return contactEmail;
	}



	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}



	@Column(name = "password", nullable = false, length=45)
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	 @Enumerated(EnumType.STRING)
	 @Column(columnDefinition = "adminType", nullable = false)
	public UserType getAdminType() {
		return adminType;
	}




	 public void setAdminType(UserType adminType) {
		this.adminType = adminType;
	}




	@Column(name = "parentTestConductorId", nullable = false, length=45)
	public Integer getParentTestConductorId() {
		return parentTestConductorId;
	}



	
	public void setParentTestConductorId(Integer parentTestConductorId) {
		this.parentTestConductorId = parentTestConductorId;
	}



	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	public Date getCreated() {
		return created;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
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
