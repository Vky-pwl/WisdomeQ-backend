

package com.icat.quest.cache.vo;

import java.io.Serializable;
import java.util.Date;

public class ApplicationExceptionClassesVo implements Serializable {

	private static final long	serialVersionUID	= 5174106621258768536L;
	private Integer				id;
	private String				exceptionClassHandler;
	private String				systemExceptionMessageWithTag;
	private String				systemExceptionMessageNoTag;
	private String				userMessage;
	private Date				created;
	private long				createdBy;
	private Date				lastModified;
	private long				lastModifiedBy;

	public ApplicationExceptionClassesVo() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param exceptionClassHandler
	 * @param systemExceptionMessageWithTag
	 * @param systemExceptionMessageNoTag
	 * @param userMessage
	 * @param created
	 * @param createdBy
	 * @param lastModified
	 * @param lastModifiedBy
	 */
	public ApplicationExceptionClassesVo(Integer id, String exceptionClassHandler, String systemExceptionMessageWithTag, String systemExceptionMessageNoTag, String userMessage,
			Date created, long createdBy, Date lastModified, long lastModifiedBy) {

		this.id = id;
		this.exceptionClassHandler = exceptionClassHandler;
		this.systemExceptionMessageWithTag = systemExceptionMessageWithTag;
		this.systemExceptionMessageNoTag = systemExceptionMessageNoTag;
		this.userMessage = userMessage;
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
	 * @return the exceptionClassHandler
	 */
	public String getExceptionClassHandler() {

		return exceptionClassHandler;
	}

	/**
	 * @param exceptionClassHandler
	 *            the exceptionClassHandler to set
	 */
	public void setExceptionClassHandler(String exceptionClassHandler) {

		this.exceptionClassHandler = exceptionClassHandler;
	}

	/**
	 * @return the systemExceptionMessageWithTag
	 */
	public String getSystemExceptionMessageWithTag() {

		return systemExceptionMessageWithTag;
	}

	/**
	 * @param systemExceptionMessageWithTag
	 *            the systemExceptionMessageWithTag to set
	 */
	public void setSystemExceptionMessageWithTag(String systemExceptionMessageWithTag) {

		this.systemExceptionMessageWithTag = systemExceptionMessageWithTag;
	}

	/**
	 * @return the systemExceptionMessageNoTag
	 */
	public String getSystemExceptionMessageNoTag() {

		return systemExceptionMessageNoTag;
	}

	/**
	 * @param systemExceptionMessageNoTag
	 *            the systemExceptionMessageNoTag to set
	 */
	public void setSystemExceptionMessageNoTag(String systemExceptionMessageNoTag) {

		this.systemExceptionMessageNoTag = systemExceptionMessageNoTag;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {

		return userMessage;
	}

	/**
	 * @param userMessage
	 *            the userMessage to set
	 */
	public void setUserMessage(String userMessage) {

		this.userMessage = userMessage;
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
