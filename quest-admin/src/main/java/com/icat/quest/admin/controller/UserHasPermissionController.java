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
import com.icat.quest.common.vo.UserHasPermissionBatchVo;
import com.icat.quest.common.vo.UserHasPermissionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.UserHasPermissionService;

@RestController
@RequestMapping(value = Constants.OPERATION_USER_HAS_PERMISSION)
public class UserHasPermissionController {

	@Autowired
	private UserHasPermissionService userHasPermissionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserHasPermissionController.class);

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateTestConductorHasPermission(
			@RequestBody Map<String, Object> requestMap, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("UserPermission Update Input: " + requestMap);	
			Integer userId = principalVo.getUserId();
			@SuppressWarnings("unchecked")
			List<Integer> userHasPermissionIdList = (List<Integer>) requestMap.get("userHasPermissionIdList");
			userHasPermissionService.updateBatchUserHasPermission(userHasPermissionIdList, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestConductorHasPermission(
			@RequestBody Map<String, Integer> requestobject, Locale locale, Model model, Principal principal) {
		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("UserPermission Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();

			Integer userHasPermissionId = requestobject.get("userHasPermissionId");
			UserHasPermissionVo userHasPermissionVo = userHasPermissionService
					.readUserHasPermission(userHasPermissionId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(userHasPermissionVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

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
			LOGGER.info("UserPermission List Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
			UserType userType = principalVo.getUserType();
			if (principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				userId = Integer.parseInt(requestobject.get("userId").toString());
				userType = UserType.valueOf(requestobject.get("userType").toString());
			}
			
			Integer examId = (Integer) requestobject.get("examId");
			Boolean active = requestobject.get("active") != null
					? Boolean.parseBoolean(requestobject.get("active").toString().trim())
					: null;

			List<UserHasPermissionVo> userHasPermissionVoList = userHasPermissionService.listUserHasPermission(userId,
					userType, active,examId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(userHasPermissionVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CREATE_BATCH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createBatchTestConductorHasPermission(
			@RequestBody UserHasPermissionBatchVo userHasPermissionBatchVo, Locale locale, Model model,
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
			LOGGER.info("UserPermission CreateBatch Input: " + userHasPermissionBatchVo);	
			Integer userId = principalVo.getUserId();

			List<Integer> idList = userHasPermissionService.craeteBatchUserHasPermission(userHasPermissionBatchVo,
					userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(idList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
