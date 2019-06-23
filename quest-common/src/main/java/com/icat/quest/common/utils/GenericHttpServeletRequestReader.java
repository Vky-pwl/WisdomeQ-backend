package com.icat.quest.common.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericHttpServeletRequestReader {
	

	
	private static final Logger logger = LoggerFactory.getLogger(GenericHttpServeletRequestReader.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static GenericHttpServeletRequest  getGenericHttpServeletRequest(HttpServletRequest httpServletRequest) {
		Boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
		Integer maxFileSize = 5 * 1024 * 1024;
		Integer maxMemSize = 5 * 1024;
		String json = null;
		
		GenericHttpServeletRequest genericHttpServeletRequest= null;
		if (isMultipart) {

			
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemSize);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxFileSize);

				
				List fileItems = upload.parseRequest(httpServletRequest);
				Iterator i = fileItems.iterator();

				StringBuffer jsonString = new StringBuffer("{");
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (fi.isFormField()) {
						String formFieldName = fi.getFieldName();
						jsonString = jsonString.append("\"" + formFieldName + "\" : " + fi.getString() + ",");
					}
				}

				jsonString = jsonString.replace(jsonString.length() - 1, jsonString.length(), "}");
				logger.info(jsonString.toString());
				
				json = jsonString.toString();
				genericHttpServeletRequest= new GenericHttpServeletRequest(json, fileItems);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			logger.warn("The Request is Not Multipart");
		}
		return genericHttpServeletRequest;
	}

}
