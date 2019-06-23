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
@Table(name = "Permission")
public class Permission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer permissionId;
	private String permissionName;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	public Permission() {
	}


	public Permission(String permissionName, Boolean active, Date created, Long createdBy, Date lastModified,
			Long lastModifiedBy) {
		super();
		this.permissionName = permissionName;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissionId", unique = true, nullable = false)
	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	

	@Column(name = "permissionName",nullable=true, length =100)
	public String getPermissionName() {
		return permissionName;
	}




	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
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
