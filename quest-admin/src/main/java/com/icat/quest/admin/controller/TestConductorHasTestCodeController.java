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
import com.icat.quest.common.vo.TestConductorHasTestCodeVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.TestConductorHasTestCodeService;

@RestController
@RequestMapping(value = Constants.OPERATION_TEST_CONDUCTOR_HAS_TEST_CODE)
public class TestConductorHasTestCodeController {

	@Autowired
	private TestConductorHasTestCodeService testConductorHasTestCodeService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConductorHasTestCodeController.class);

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateTestConductorHasTestCode(
			@RequestBody TestConductorHasTestCodeVo testConductorHasTestCodeVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductorHasTestCode Update Input: " + testConductorHasTestCodeVo);	
			Integer userId = principalVo.getUserId();
		
			Boolean flag = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = true;
			}
			Integer id = testConductorHasTestCodeService.updateTestConductorHasTestCode(testConductorHasTestCodeVo,
					userId, flag);

			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_USER_ASSIGNED_TEST_CODE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> assignTestCodeOfUser(@RequestBody Map<String, Object> requestParam,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductorHasTestCode assign Input: " + requestParam);	
			Integer userId = principalVo.getUserId();
		
			Boolean flag = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = true;
			}
			Integer testConductorLicenseId = (Integer)requestParam.get("testConductorLicenseId");
			@SuppressWarnings("unchecked")
			List<Integer> userIdList = (List<Integer>)requestParam.get("userIdList") ;
			String tinyKey = null;
			testConductorHasTestCodeService.assignedUserTestCode(userIdList,testConductorLicenseId, userId,flag, tinyKey);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_USER_DEACTIVATE_TEST_CODE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deactiveTestConductorHasTestCode(
			@RequestBody Map<String, Object> requestParam, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductorHasTestCode Deactive Input: " + requestParam);	
			Integer userId = principalVo.getUserId();
		
			Boolean flag = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = true;
			}
			@SuppressWarnings("unchecked")
			List<Integer> testConductorHasTestCodeIdList = requestParam.get("testConductorHasTestCodeIdList") != null
					? (List<Integer>) requestParam.get("testConductorHasTestCodeIdList")
					: null;

			testConductorHasTestCodeService.deactiveTestConductorHasTestCode(testConductorHasTestCodeIdList, userId,flag);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_TEST_CONDUCTOR_LICENCE_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getTestCodeByTestConductorLicenseId(
			@RequestBody Map<String, Integer> request, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductorHasTestCode List Input: " + request);	
			Integer userId = principalVo.getUserId();
		
			Boolean flag = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = true;
			}
			Integer testConductorLicenseId = request.get("testConductorLicenseId") ;
			Integer pageNo = request.get("pageNo") ;
			Integer pageSize = request.get("pageSize") ;
									
			Map<String,Object> testConductorHasTestCodeVos = testConductorHasTestCodeService
					.getTestCodeByTestConductorLicenseId(testConductorLicenseId, userId,flag,pageNo,pageSize);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorHasTestCodeVos)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestConductorHasTestCode(
			@RequestBody Map<String, Integer> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductorHasTestCode Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
		
			Integer testConductorHasTestCodeId = requestobject.get("testConductorHasTestCodeId");
			TestConductorHasTestCodeVo testConductorHasTestCodeVo = testConductorHasTestCodeService
					.readTestConductorHasTestCode(testConductorHasTestCodeId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorHasTestCodeVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	
}
