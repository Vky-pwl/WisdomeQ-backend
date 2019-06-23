package com.icat.quest.admin.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icat.quest.admin.service.PublishExamLicenseService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_LICENCE)
public class PublishExamLicenseController {

	@Autowired
	private PublishExamLicenseService publishExamLicenseService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PublishExamLicenseController.class);

	@RequestMapping(value = Constants.OPERATION_PUBLISH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> publishExamLicense(@RequestBody Map<String, Object> requestBody,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			Integer testConductorLicenseId = requestBody.get("testConductorLicenseId") != null
					? Integer.parseInt(requestBody.get("testConductorLicenseId").toString())
					: null;
			Long startTime = requestBody.get("startTime") != null
					? Long.parseLong(requestBody.get("startTime").toString())
					: null;
			Long endTime = requestBody.get("endTime") != null ? Long.parseLong(requestBody.get("endTime").toString())
					: null;
			if (testConductorLicenseId == null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();
			}
			Boolean flag = true;
			if (principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = false;
			}
			String code = publishExamLicenseService.publishExamLicense(testConductorLicenseId, startTime, endTime,
					userId, flag);
			if (code != null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(code).build();
			} else {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_DEACTIVATE + "/{testConductorLicenseId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> unpublishExamLicense(
			@PathVariable("testConductorLicenseId") Integer testConductorLicenseId, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			if (testConductorLicenseId == null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();
			}
			publishExamLicenseService.deactivePublishExamLicense(testConductorLicenseId);

			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ + "/{testConductorLicenseId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getExamLicense(
			@PathVariable("testConductorLicenseId") Integer testConductorLicenseId, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			if (testConductorLicenseId == null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();
			}
			Map<String, Object> response = publishExamLicenseService.getPublishLicense(testConductorLicenseId);
			if (response.containsKey(Constants.STATUS_ERROR)) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).object(response).build();
			}
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(response).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
