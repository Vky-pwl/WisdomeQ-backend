

package com.icat.quest.cache.vo;

import java.io.Serializable;
import java.util.Date;

public class ApplicationClassesVo implements Serializable {

	private static final long	serialVersionUID	= -8033728903766385062L;
	private Integer				id;
	private String				className;
	private String				debugLevel;
	private Date				created;
	private long				createdBy;
	private Date				lastModified;
	private long				lastModifiedBy;

	public ApplicationClassesVo() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public ApplicationClassesVo(Integer id) {

		this.id = id;
	}

	/**
	 * @param className
	 * @param debugLevel
	 * @param created
	 * @param createdBy
	 * @param lastModified
	 * @param lastModifiedBy
	 */
	public ApplicationClassesVo(String className, String debugLevel, Date created, long createdBy, Date lastModified, long lastModifiedBy) {

		this.className = className;
		this.debugLevel = debugLevel;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {

		this.id = id;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {

		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {

		this.className = className;
	}

	/**
	 * @return the debugLevel
	 */
	public String getDebugLevel() {

		return debugLevel;
	}

	/**
	 * @param debugLevel
	 *            the debugLevel to set
	 */
	public void setDebugLevel(String debugLevel) {

		this.debugLevel = debugLevel;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {

		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {

		this.created = created;
	}

	/**
	 * @return the createdBy
	 */
	public long getCreatedBy() {

		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(long createdBy) {

		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {

		return lastModified;
	}

	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(Date lastModified) {

		this.lastModified = lastModified;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public long getLastModifiedBy() {

		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(long lastModifiedBy) {

		this.lastModifiedBy = lastModifiedBy;
	}

}
