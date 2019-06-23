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

import com.icat.quest.admin.service.ExamCategoryService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamCategoryVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_CATEGORY)
public class ExamCategoryController {

	@Autowired
	private ExamCategoryService examCategoryService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamCategoryController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamCategory(@RequestBody ExamCategoryVo examCategoryVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExamCategory Create Input: " + examCategoryVo);	
			Integer id = examCategoryService.createExamCategory(examCategoryVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExamCategory(@RequestBody ExamCategoryVo examCategoryVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExamCategory Update Input: " + examCategoryVo);
			Integer id = examCategoryService.updateExamCategory(examCategoryVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamCategory(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
			Integer categoryId = requestobject.get("examCategoryId");
			LOGGER.info("ExamCategory By CategoryId: " + categoryId);
			ExamCategoryVo examCategoryVo = examCategoryService.readExamCategory(categoryId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examCategoryVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_CATEGORY_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSubCategoryByCategoryId(
			@RequestBody Map<String, Object> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
			Integer categoryId = (Integer)requestobject.get("examCategoryId");
			Boolean active = (Boolean)requestobject.get("active");
			LOGGER.info("ExamCategory List By CategoryId: " + categoryId);
			List<ExamCategoryVo> examCategoryVoList = examCategoryService.listSubcategory(categoryId, userId,active);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(examCategoryVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamCategory(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
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
			LOGGER.info("ExamCategory List Input: " + requestobject);
			Map<String, Object> examCategoryVoList = examCategoryService.listExamCategory(pageNo, pageSize, searchKey,
					active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examCategoryVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_SUB_CATEGORY_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSubCategory(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
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
			LOGGER.info("ExamSubCategory List Input: " + requestobject);
			Map<String, Object> examCategoryVoList = examCategoryService.listExamSubCategory(pageNo, pageSize,
					searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examCategoryVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
