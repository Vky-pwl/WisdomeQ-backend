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
@Table(name = "UserHasPermission")
public class UserHasPermission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userHasPermissionId;
	private Permission permission;
	private Exam exam;
	private Integer userId;
	private UserType userType;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;

	public UserHasPermission() {
	}

	public UserHasPermission(Permission permission, Exam exam, Integer userId, UserType userType, Boolean active,
			Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.permission = permission;
		this.exam = exam;
		this.userId = userId;
		this.userType = userType;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userHasPermissionId", unique = true, nullable = false)
	public Integer getUserHasPermissionId() {
		return userHasPermissionId;
	}

	public void setUserHasPermissionId(Integer userHasPermissionId) {
		this.userHasPermissionId = userHasPermissionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examId", nullable = false)
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permissionId")
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Column(name = "active", nullable = false)
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
