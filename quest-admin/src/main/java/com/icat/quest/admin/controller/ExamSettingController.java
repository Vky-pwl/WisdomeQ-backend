/*package com.icat.quest.admin.controller;

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
import com.icat.quest.common.vo.ExamSettingVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.ExamSettingService;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_SETTING)
public class ExamSettingController {

	@Autowired
	private ExamSettingService examSettingService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamSettingController.class);


	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listTestConductorHasPermission(
			@RequestBody Map<String, Object> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("ExamSetting List Input: " + requestobject);	
			
			Integer examId = (Integer) requestobject.get("examId");
			Integer pageNo = (Integer) requestobject.get("pageNo");
			Integer pageSize = (Integer) requestobject.get("pageSize");

			Map<String, Object> examSettingVoList = examSettingService.getListByExamId(examId, principalVo.getUserId(), pageNo, pageSize);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSettingVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CREATE_BATCH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createBatchTestConductorHasPermission(
			@RequestBody ExamSettingVo examSettingVo, Locale locale, Model model,
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
			LOGGER.info("ExamSetting Create Input: " + examSettingVo);	
			Integer userId = principalVo.getUserId();

			List<Integer> idList = examSettingService.createBatchExamSetting(examSettingVo,userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(idList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
*/