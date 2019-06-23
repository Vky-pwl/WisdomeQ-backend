package com.icat.quest.admin.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icat.quest.admin.service.ExplanationDescriptionService;
import com.icat.quest.admin.utils.ConverterHttpServletRequestToCustomObject;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExplanationDescriptionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.UserHasPermissionService;

@RestController
@RequestMapping(value = Constants.OPERATION_EXPLANATION_DESCRIPTION)
public class ExplanationDescriptionController {

	@Autowired
	private ExplanationDescriptionService explanationDescriptionService;
	@Autowired
	private UserHasPermissionService   userHasPermissionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExplanationDescriptionController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExplanationDescription(HttpServletRequest httpServletRequest,
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
		
			ExplanationDescriptionVo explanationDescriptionVo = ConverterHttpServletRequestToCustomObject
					.getExplanationDescriptionVo(httpServletRequest);
			LOGGER.info("ExplanationDesc Create Input: " + explanationDescriptionVo);	
			Integer id = explanationDescriptionService.createExplanationDescription(explanationDescriptionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExplanationDescription(HttpServletRequest httpServletRequest,
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
			ExplanationDescriptionVo explanationDescriptionVo = ConverterHttpServletRequestToCustomObject
					.getExplanationDescriptionVo(httpServletRequest);
			LOGGER.info("ExplanationDesc Update Input: " + explanationDescriptionVo);	
			Integer id = explanationDescriptionService.updateExplanationDescription(explanationDescriptionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> readExplanationDescription(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExplanationDesc Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
			Integer explanationDescriptionId = requestobject.get("explanationId");
			ExplanationDescriptionVo explanationDescriptionVo = explanationDescriptionService
					.readExplanationDescription(explanationDescriptionId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(explanationDescriptionVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ_BY_QUESTIONID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> readExplanationDescriptionByQuestionId(
			@RequestBody Map<String, Integer> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExplanationDesc Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
			Integer questionId = requestobject.get("questionId");
			Integer examId = requestobject.get("examId");
			if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				boolean isEligble =	userHasPermissionService.isPermission(principalVo.getUserId(), examId, principalVo.getUserType(), 3);
			if(!isEligble) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("User have no permission for Get Certifcate ").build();
			}
			}
			ExplanationDescriptionVo explanationDescriptionVo = explanationDescriptionService
					.readExplanationDescriptionByQuestionId(questionId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(explanationDescriptionVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExplanationDescription(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExplanationDesc List Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
		
			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;

			Map<String, Object> explanationDescriptionVoList = explanationDescriptionService
					.listExplanationDescription(pageNo, pageSize, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success)
					.object(explanationDescriptionVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
