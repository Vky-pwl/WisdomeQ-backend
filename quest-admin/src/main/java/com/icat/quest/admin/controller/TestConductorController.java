package com.icat.quest.admin.controller;

import java.security.Principal;
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

import com.icat.quest.admin.service.TestConductorService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.common.vo.TestConductorVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_TEST_CONDUCTOR)
public class TestConductorController {

	@Autowired
	private TestConductorService testConductorService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConductorController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestConductor(@RequestBody TestConductorVo testConductorVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null ) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductor Create Input: " + testConductorVo);	
			Integer userId = principalVo.getUserId();
			if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				if( principalVo.getCollegeId() == null && principalVo.getUserType().equals(UserType.TESTCONDUCTOR)) {
					LOGGER.warn("Access Not Authorized User");
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();		
			}
				testConductorVo.setCollegeVo(new CollegeVo(principalVo.getCollegeId()));
				testConductorVo.setParentTestConductorId(principalVo.getUserId());
				
			} 
			if(testConductorVo.getAdminType().equals(UserType.TESTCONDUCTOR) && testConductorVo.getParentTestConductorId() == null) {
				LOGGER.warn("ParentId should not be null");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("ParentId should not be null").build();	
			}
		
			Integer id = testConductorService.craeteTestConductor(testConductorVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateTestConductor(@RequestBody TestConductorVo testConductorVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null ) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductor Update Input: " + testConductorVo);	
			
			Integer userId = principalVo.getUserId();
		if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
			if(principalVo.getCollegeId() == null ||testConductorVo.getCollegeVo() == null || testConductorVo.getCollegeVo().getCollegeId() == null ||!principalVo.getCollegeId().equals(testConductorVo.getCollegeVo().getCollegeId())) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();	
			}
			
		}
			Integer id = testConductorService.updateTestConductor(testConductorVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> readTestConductor(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductor Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
		
			Integer testConductorId = requestobject.get("testConductorId");
			TestConductorVo testConductorVo = testConductorService.readTestConductor(testConductorId, userId);
			if(principalVo.getUserType().equals(UserType.ADMIN) && testConductorVo.getCollegeVo().getCollegeId().equals(principalVo.getCollegeId())) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorVo).build();
					
			}
			else if(principalVo.getUserType().equals(UserType.TESTCONDUCTOR) && !testConductorVo.getTestConductorId().equals(userId)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
		
			}
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listTestConductor(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null ) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductor List Input: " + requestobject);	
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
			String adminType = requestobject.get("adminType") != null ? requestobject.get("adminType").toString().trim()
					: null;
			if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String,Object> testConductorVos = testConductorService.listExaminerByAdminId(pageNo, pageSize,
						 active, userId, adminType);
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorVos).build();
			}

			Map<String, Object> testConductorCompositeVoList = testConductorService.listTestConductor(pageNo, pageSize,
					searchKey, active, userId, adminType);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success)
					.object(testConductorCompositeVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_TESTCONDUCTOR_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestConductor(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null ) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer testConductorId = null;
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("TestConductor List Input: " + requestobject);	
			
			if (principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				testConductorId = requestobject.get("testConductorId");
			} else {
				testConductorId = principalVo.getUserId();
			}
			
			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Boolean active = requestobject.get("active") != null
					? Boolean.parseBoolean(requestobject.get("active").toString().trim())
					: null;
			String adminType = requestobject.get("adminType") != null ? requestobject.get("adminType").toString().trim()
					: null;
	

			Map<String,Object> testConductorVos = testConductorService.listExaminerByAdminId(pageNo, pageSize,
					 active, testConductorId, adminType);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(testConductorVos).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
