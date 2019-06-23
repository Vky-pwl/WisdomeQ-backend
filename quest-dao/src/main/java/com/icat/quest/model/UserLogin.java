package com.icat.quest.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.UserType;

@Entity
@Table(name = "UserLogin")
public class UserLogin implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int		id;
	private Integer	userId;
	private UserType userType;
	private Date	logoffTime;
	private Date	tokenExpiryTime;
	private Date	created;
	private Long	createdBy;
	private Date	lastModified;
	private Long	lastModifiedBy;
	
	public UserLogin() {

	}

	
	
	public UserLogin(Integer userId, UserType userType,Date tokenExpiryTime, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.userId = userId;
		this.userType = userType;
		this.tokenExpiryTime = tokenExpiryTime;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	 @Enumerated(EnumType.STRING)
	 @Column(columnDefinition = "userType", nullable = false)
	public UserType getUserType() {
		return userType;
	}



	public void setUserType(UserType userType) {
		this.userType = userType;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logoffTime", length = 19)
	public Date getLogoffTime() {

		return this.logoffTime;
	}

	public void setLogoffTime(Date logoffTime) {

		this.logoffTime = logoffTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tokenExpiryTime", length = 19)
	public Date getTokenExpiryTime() {

		return this.tokenExpiryTime;
	}

	public void setTokenExpiryTime(Date tokenExpiryTime) {

		this.tokenExpiryTime = tokenExpiryTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {

		return this.created;
	}

	public void setCreated(Date created) {

		this.created = created;
	}

	@Column(name = "createdBy", nullable = false)
	public Long getCreatedBy() {

		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {

		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModified", nullable = false, length = 19)
	public Date getLastModified() {

		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {

		this.lastModified = lastModified;
	}

	
	@Column(name = "lastModifiedBy", nullable = false)
	public Long getLastModifiedBy() {

		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {

		this.lastModifiedBy = lastModifiedBy;
	}

}