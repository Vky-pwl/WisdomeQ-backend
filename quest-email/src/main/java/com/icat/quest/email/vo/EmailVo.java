package com.icat.quest.email.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subject;
	private List<String> ccList = new ArrayList<>();
	private List<String> bccList = new ArrayList<>();
	private List<String> toList = new ArrayList<>();
	private String priority;
	private String messageBody;
	public EmailVo() {
		super();
	}
	public EmailVo(String subject, List<String> ccList, List<String> bccList, List<String> toList, String priority,
			String messageBody) {
		super();
		this.subject = subject;
		this.ccList = ccList;
		this.bccList = bccList;
		this.toList = toList;
		this.priority = priority;
		this.messageBody = messageBody;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getCcList() {
		return ccList;
	}
	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}
	public List<String> getBccList() {
		return bccList;
	}
	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}
	public List<String> getToList() {
		return toList;
	}
	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
