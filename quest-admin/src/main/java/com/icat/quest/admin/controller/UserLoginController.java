package com.icat.quest.admin.controller;

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

import com.icat.quest.admin.service.ExamSectionHasQuestionService;
import com.icat.quest.admin.service.ExamService;
import com.icat.quest.auth.login.service.AuthServiceImpl;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.UserType;

@RestController
public class UserLoginController {

	@Autowired
	private AuthServiceImpl authServiceImpl;
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamSectionHasQuestionService		examSectionHasQuestionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamCategoryController.class);

	@RequestMapping(value = Constants.OPERATION_TEST_CONDUCTOR_LOGIN
			+ Constants.OPERATION_LOGIN, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> loginTestConductor(@RequestBody Map<String, String> requestobject,
			Locale locale, Model model) {

		try {
			LOGGER.info("UserLogin Input: "+requestobject.get(Constants.REQUEST_USERNAME));
			requestobject.put(Constants.REQUEST_USER_TYPE, UserType.SUPERADMIN.name());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success)
					.object(authServiceImpl.getAuthTokenForLogin(requestobject)).build();

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CANDIDATE_LOGIN
			+ Constants.OPERATION_LOGIN, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> loginUserConductor(@RequestBody Map<String, String> requestobject,
			Locale locale, Model model) {

		try {
			LOGGER.info("UserLogin Input: "+requestobject.get(Constants.REQUEST_USERNAME));
			requestobject.put(Constants.REQUEST_USER_TYPE, UserType.CANDIDATE.name());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success)
					.object(authServiceImpl.getAuthTokenForLogin(requestobject)).build();

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_EXAM_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> topExamList(@RequestBody Map<String, String> requestobject, Locale locale,
			Model model) {

		try {
			LOGGER.info("ExamList Input: "+requestobject);
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

			Map<String, Object> examVoList = examService.listExam(pageNo, pageSize, searchKey, active, null, null,true);

			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examVoList).build();

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	/*
	 * @RequestMapping(value = "/update", method = RequestMethod.POST)
	 * public @ResponseBody Map<String, Object> update(@RequestBody Map<String,
	 * Object> requestobject, Locale locale, Model model) {
	 * 
	 * try { Long remainingTime = requestobject.get("remainingTime") != null ?
	 * Long.parseLong(requestobject.get("remainingTime").toString()) : null; Integer
	 * examId = (Integer) requestobject.get("examId"); Integer userId = (Integer)
	 * requestobject.get("userId"); Map<String, Object> params = new HashMap<>();
	 * params.put("_1_remainingTime", remainingTime); params.put("_2_examId",
	 * examId); params.put("_3_userId", userId); testConductorHasTestCodeDao
	 * .updateBySqlQuery(TestConductorHasTestCodeDao.
	 * updateRemainingTimeByUserIdAndExamId, params); return new
	 * ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
	 * 
	 * } catch (Exception e) { return new
	 * ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.
	 * getMessage()).build();
	 * 
	 * } }
	 */
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> test(@RequestBody ExamSectionHasQuestionVo examSectionHasQuestionVo,
			Locale locale, Model model) {

		try {
			examSectionHasQuestionService.createExamSectionHasQuestion(examSectionHasQuestionVo, 1000);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
	} catch (Exception e) {
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
}
