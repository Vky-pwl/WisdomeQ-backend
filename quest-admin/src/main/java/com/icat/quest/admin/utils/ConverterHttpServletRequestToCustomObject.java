/**
 * 
 */
package com.icat.quest.admin.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.common.vo.ExplanationDescriptionVo;
import com.icat.quest.common.vo.QuestionBankVo;


public class ConverterHttpServletRequestToCustomObject {

	
	public static ExamSectionHasQuestionVo getExamSectionHasQuestionVo(HttpServletRequest httpServletRequest) {
		
		ExamSectionHasQuestionVo examSectionHasQuestionVo = new ExamSectionHasQuestionVo();
	
		int maxFileSize = 15000 * 1024;
		int maxMemSize = 4 * 1024;

		try {
			//String contentType = httpServletRequest.getContentType();
			boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

		
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemSize);

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxFileSize);

				List<FileItem> fileItems = upload.parseRequest(httpServletRequest);

				Iterator<FileItem> i = fileItems.iterator();
				Gson gson = new Gson();
				
				while (i.hasNext()) {

					FileItem fi = (FileItem) i.next();

					if (fi.isFormField()) {
						String formFieldName = fi.getFieldName();

						switch (formFieldName) {

						case "active":
							examSectionHasQuestionVo.setActive(Boolean.parseBoolean(new String(fi.get())));
							break;
						case "examSectionHasQuestionId":
							examSectionHasQuestionVo.setExamSectionHasQuestionId(Integer.parseInt(new String(fi.get())));
							break;
							
						case "examSectionVo":
							examSectionHasQuestionVo.setExamSectionVo(gson.fromJson(new String(fi.get()), ExamSectionVo.class));
						
							break;
							
						case "questionNumber":
							examSectionHasQuestionVo.setQuestionNumber(Integer.parseInt(new String(fi.get())));
							break;
						case "marks":
							examSectionHasQuestionVo.setMarks(Float.parseFloat(new String(fi.get())));
							break;
									
						case "questionBankVo":
							examSectionHasQuestionVo.setQuestionBankVo(gson.fromJson(new String(fi.get()), QuestionBankVo.class));
							break;

						default:
							break;
						}
					}
				}
				
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();

					if (!fi.isFormField()) {

						String fileName = fi.getName();

				//		String fileContentType = fi.getContentType();
						byte[] fileData = fi.get();
						switch (fileName) {
						case "questionDescriptionImage":
							examSectionHasQuestionVo.getQuestionBankVo().getQuestionDescriptionVo().setDescriptionImage(fileData);	
							break;
						case "explanationImage":
							examSectionHasQuestionVo.getQuestionBankVo().getExplanation().setExplanationImage(fileData);	
							break;
						
						default:
							break;
						}
				
					}}
			}

		
	}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return examSectionHasQuestionVo;
	}

	public static QuestionBankVo getQuestionBankVo(HttpServletRequest httpServletRequest) {
		
		QuestionBankVo questionBankVo = new QuestionBankVo();
	
		int maxFileSize = 15000 * 1024;
		int maxMemSize = 4 * 1024;

		try {
			//String contentType = httpServletRequest.getContentType();
			boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

		
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemSize);

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxFileSize);

				List<FileItem> fileItems = upload.parseRequest(httpServletRequest);

				Iterator<FileItem> i = fileItems.iterator();
				while (i.hasNext()) {

					FileItem fi = (FileItem) i.next();

					if (fi.isFormField()) {
						String formFieldName = fi.getFieldName();

						switch (formFieldName) {

									
						case "questionBankVo":
							Gson gson = new Gson();
						questionBankVo	=(gson.fromJson(new String(fi.get()), QuestionBankVo.class));
							break;

						default:
							break;
						}
					}
				}
				
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();

					if (!fi.isFormField()) {

						String fileName = fi.getName();

				//		String fileContentType = fi.getContentType();
						byte[] fileData = fi.get();
						switch (fileName) {
						case "explanationImage":
							questionBankVo.getExplanation().setExplanationImage(fileData);	
							break;
						case "questionDescriptionImage":
							questionBankVo.getQuestionDescriptionVo().setDescriptionImage(fileData);	
								break;
							
						default:
							break;
						}
				
					}}
			}

		
	}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return questionBankVo;
	}

	
public static ExplanationDescriptionVo getExplanationDescriptionVo(HttpServletRequest httpServletRequest) {
		
	ExplanationDescriptionVo explanationDescriptionVo = new ExplanationDescriptionVo();
	
		int maxFileSize = 15000 * 1024;
		int maxMemSize = 4 * 1024;

		try {
			//String contentType = httpServletRequest.getContentType();
			boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

		
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemSize);

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxFileSize);

				List<FileItem> fileItems = upload.parseRequest(httpServletRequest);

				Iterator<FileItem> i = fileItems.iterator();
				while (i.hasNext()) {

					FileItem fi = (FileItem) i.next();

					if (fi.isFormField()) {
						String formFieldName = fi.getFieldName();

						switch (formFieldName) {

									
						case "explanationDescriptionVo":
							Gson gson = new Gson();
							explanationDescriptionVo	=(gson.fromJson(new String(fi.get()), ExplanationDescriptionVo.class));
							break;

						default:
							break;
						}
					}
				}
				
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();

					if (!fi.isFormField()) {

						String fileName = fi.getName();

				//		String fileContentType = fi.getContentType();
						byte[] fileData = fi.get();
						switch (fileName) {
						case "explanationImage":
							explanationDescriptionVo.setExplanationImage(fileData);	
							break;
						
						default:
							break;
						}
				
					}}
			}

		
	}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return explanationDescriptionVo;
	}

	
}
