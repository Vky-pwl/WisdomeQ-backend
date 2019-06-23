package com.icat.quest.admin.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
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

import com.icat.quest.admin.service.ExamService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamDao;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM)
public class ExamController {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamDao 		examDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExam(@RequestBody ExamVo examVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("Exam Create Input: " + examVo);
			
			Integer id = examService.createExam(examVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExam(@RequestBody ExamVo examVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("Exam Update Input: " + examVo);
			
			Integer id = examService.updateExam(examVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_PUBLISH+"/{examId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> publishExam(@PathVariable("examId") Integer examId, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("Publish Exam ExamId: " + examId);
			
			examService.publishUpdate(examId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExam(@RequestBody Map<String, Integer> requestobject, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null ) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			
			Integer examId = requestobject.get("examId");
			LOGGER.info("Read Exam ExamId: " + examId);
			
			if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
			Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
			paramsKayAndValues.put("_1_testConductorId", userId);
			@SuppressWarnings("unchecked")
			List<Integer> examIdList = (List<Integer>) examDao.listSingleRowResult(ExamDao.findExamIdsByTcId, paramsKayAndValues); 
			if(examIdList == null || !examIdList.contains(examId)) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();		
			}
			}
			ExamVo examVo = examService.readExam(examId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExam(@RequestBody Map<String, Object> requestobject, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
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
			Boolean publish = null;
			
			if(!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				publish = true;
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				LOGGER.info("ExamList Input : " + requestobject+" TestConductor: "+userId);
				@SuppressWarnings("unchecked")
				List<Integer> examIdList = (List<Integer>) examDao.listSingleRowResult(ExamDao.findExamIdsByTcId, paramsKayAndValues); 
				Map<String, Object> examVoList = examService.listExamByExamIdList(pageNo, pageSize, searchKey, active, userId,examIdList, principalVo.getUserType(), true);
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examVoList).build();
			}
			LOGGER.info("ExamList Input : " + requestobject);
			Map<String, Object> examVoList = examService.listExam(pageNo, pageSize, searchKey, active, userId,principalVo.getUserType(), publish);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
}
