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

import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamSettingsVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.ExamHasSettingsService;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_SETTING)
public class ExamHasSettingsController {

	@Autowired
	private ExamHasSettingsService examHasSettingsService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamHasSettingsController.class);


	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listExamSettings(Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
			List<ExamSettingsVo> examSettingVoList = examHasSettingsService.listExamSettings();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(examSettingVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamSettings(
			@RequestBody ExamSettingsVo examSettingsVo, Locale locale, Model model,
			Principal principal) {
		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExamSetting Create Input: " + examSettingsVo);	
			Integer userId = principalVo.getUserId();

			Integer id = examHasSettingsService.createExamSetting(examSettingsVo,userId);
			if(id == null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Technical issue").build();
			}
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExamSettings(
			@RequestBody ExamSettingsVo examSettingsVo, Locale locale, Model model,
			Principal principal) {
		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExamSetting Update Input: " + examSettingsVo);	
			Integer userId = principalVo.getUserId();

			examHasSettingsService.updateExamSetting(examSettingsVo,userId);
			
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	
}
