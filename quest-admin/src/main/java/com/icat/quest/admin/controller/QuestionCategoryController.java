package com.icat.quest.admin.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icat.quest.admin.service.QuestionCategoryService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_QUESTION_CATEGORY)
public class QuestionCategoryController {

	@Autowired
	private QuestionCategoryService questionCategoryService;
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionCategoryController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createQuestionCategory(@RequestBody QuestionCategoryVo questionCategoryVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("QuestionCategory Create Input: " + questionCategoryVo);	
			Integer id = questionCategoryService.createQuestionCategory(questionCategoryVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateQuestionCategory(@RequestBody QuestionCategoryVo questionCategoryVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("QuestionCategory Update Input: " + questionCategoryVo);	
			Integer id = questionCategoryService.updateQuestionCategory(questionCategoryVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createQuestionCategory(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("QuestionCategory Read Input: " + requestobject);	
			Integer categoryId = requestobject.get("questionCategoryId");
			QuestionCategoryVo questionCategoryVo = questionCategoryService.readQuestionCategory(categoryId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(questionCategoryVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_CATEGORY_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamCategory(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("QuestionCategory List Input By CategoryId: " + requestobject);	
			Integer categoryId = (Integer)requestobject.get("questionCategoryId");
			Boolean active = (Boolean)requestobject.get("active");
			
			List<QuestionCategoryVo> questionCategoryVoList = questionCategoryService.listSubcategory(categoryId,
					userId,active);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(questionCategoryVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();
		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listQuestionCategory(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
		
			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			String searchKey = requestobject.get("searchKey") != null ? requestobject.get("searchKey").toString().trim()
					: null;
			Boolean active = requestobject.get("active") != null
					? Boolean.parseBoolean(requestobject.get("active").toString().trim())
					: null;
			LOGGER.info("QuestionCategory List Input: " + requestobject);	
					
			Map<String, Object> questionCategoryVoList = questionCategoryService.listQuestionCategory(pageNo, pageSize,
					searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(questionCategoryVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_SUB_CATEGORY_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listQuestionSubCategory(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
		
			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			String searchKey = requestobject.get("searchKey") != null ? requestobject.get("searchKey").toString().trim()
					: null;
			Boolean active = requestobject.get("active") != null
					? Boolean.parseBoolean(requestobject.get("active").toString().trim())
					: null;
			LOGGER.info("QuestionSubCategory List Input: " + requestobject);	
					
			Map<String, Object> questionCategoryVoList = questionCategoryService.listQuestionSubCategory(pageNo,
					pageSize, searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(questionCategoryVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
