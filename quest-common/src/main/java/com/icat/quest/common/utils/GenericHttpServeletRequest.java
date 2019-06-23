package com.icat.quest.common.utils;

import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class GenericHttpServeletRequest {
	
	String json;
	List<FileItem> fileItems;
	

	public GenericHttpServeletRequest(String json, List<FileItem> fileItems) {
		super();
		this.json = json;
		this.fileItems = fileItems;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public List<FileItem> getFileItems() {
		return fileItems;
	}
	public void setFileItems(List<FileItem> fileItems) {
		this.fileItems = fileItems;
	}
	

}
