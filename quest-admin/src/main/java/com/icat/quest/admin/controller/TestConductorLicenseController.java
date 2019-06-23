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

import com.icat.quest.admin.service.TestConductorLicenseService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.TestConductorLicenseVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_TEST_CONDUCTOR_LICENCE)
public class TestConductorLicenseController {

	@Autowired
	private TestConductorLicenseService testConductorLicenseService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConductorLicenseController.class);

	@RequestMapping(value = Constants.OPERATION_EXTERNAL_LICENSE_ASSIGN, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> assignExternalLicense(@RequestBody Map<String, Integer> requestBody,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense External Create Input: " + requestBody);
			Integer userId = principalVo.getUserId();
			Integer candidateId = requestBody.get("candidateId");
			Integer examId = requestBody.get("examId");
			Integer id = testConductorLicenseService.assignExternalLicense(candidateId, examId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_EXTERNAL_LICENSE_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getExternalLicenses(@RequestBody Map<String, Integer> requestBody,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense External List Input: " + requestBody);
			Integer candidateId = requestBody.get("candidateId");
			Integer pageSize = requestBody.get("pageSize");
			Integer pageNo = requestBody.get("pageNo");
			Map<String, Object> testConductorLicenseVos = testConductorLicenseService
					.getExternalLicenseList(candidateId, principalVo.getUserId(), pageSize, pageNo);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorLicenseVos)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_ADMIN_ASSIGNED_LICENSE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> assignAdminLicense(
			@RequestBody TestConductorLicenseVo testConductorLicenseVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense Admin Create Input: " + testConductorLicenseVo);
			Integer userId = principalVo.getUserId();

			Integer id = testConductorLicenseService.assignAdminLicense(testConductorLicenseVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_TC_ASSIGNED_LICENSE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> assignTestConductorLicense(
			@RequestBody TestConductorLicenseVo testConductorLicenseVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| principalVo.getUserType().equals(UserType.TESTCONDUCTOR)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense Create Input: " + testConductorLicenseVo);
			Integer userId = principalVo.getUserId();

			if (!principalVo.getUserType().equals(UserType.ADMIN)) {
				testConductorLicenseVo.setParentTestConductorId(userId);
			}
			if (testConductorLicenseVo == null || testConductorLicenseVo.getParentTestConductorId() == null
					|| testConductorLicenseVo.getTestConductorVo() == null
					|| testConductorLicenseVo.getTestConductorVo().getTestConductorId() == null
					|| testConductorLicenseVo.getExamVo() == null
							&& testConductorLicenseVo.getExamVo().getExamId() == null) {
				LOGGER.warn("Invalid Input");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();
			}

			Integer id = testConductorLicenseService.assignTestConductorLicense(testConductorLicenseVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateTestConductorLicense(
			@RequestBody TestConductorLicenseVo testConductorLicenseVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense Update Input: " + testConductorLicenseVo);
			Integer userId = principalVo.getUserId();
			Boolean flag = true;
			if (principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				flag = false;
			}

			Integer id = testConductorLicenseService.updateTestConductorLicense(testConductorLicenseVo, userId, flag);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestConductorLicense(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense Read Input: " + requestobject);
			Integer userId = principalVo.getUserId();

			Integer testConductorLicenseId = requestobject.get("testConductorLicenseId");
			TestConductorLicenseVo testConductorLicenseVo = testConductorLicenseService
					.readTestConductorLicense(testConductorLicenseId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorLicenseVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listTestConductorLicense(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense List Input: " + requestobject);
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

			Map<String, Object> testConductorLicenseVoList = testConductorLicenseService
					.listTestConductorLicense(pageNo, pageSize, searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorLicenseVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_TEST_CONDUCTOR_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listByTestConductorId(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense List TCID Input: " + requestobject);
			Integer userId = principalVo.getUserId();
			Integer pageNo = requestobject.get("pageNo");
			Integer pageSize = requestobject.get("pageSize");
			Integer testConductorId = requestobject.get("testConductorId");

			if (principalVo.getUserType().equals(UserType.TESTCONDUCTOR)) {
				testConductorId = userId;
			}

			Map<String, Object> testConductorLicenseVoList = testConductorLicenseService
					.listBytestConductorId(testConductorId, pageNo, pageSize, userId, principalVo.getUserType());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorLicenseVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_EXAM_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listByExamId(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("TestConductorLicense List TCID Input: " + requestobject);
			Integer userId = principalVo.getUserId();
			Integer testConductorId = requestobject.get("testConductorId");
			Integer examId = requestobject.get("examId");

			if (principalVo.getUserType().equals(UserType.TESTCONDUCTOR)) {
				testConductorId = userId;
			}

			List<TestConductorLicenseVo> testConductorLicenseVoList = testConductorLicenseService
					.listByExamId(testConductorId, examId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorLicenseVoList)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
