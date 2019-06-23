package com.icat.quest.admin.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
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

import com.icat.quest.admin.service.ExamSectionHasQuestionService;
import com.icat.quest.admin.utils.ConverterHttpServletRequestToCustomObject;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamSectionDao;
import com.icat.quest.dao.ExamSectionHasQuestionDao;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_SECTION_HAS_QUESTION)
public class ExamSectionHasQuestionController {

	@Autowired
	private ExamSectionHasQuestionService examSectionHasQuestionService;
	@Autowired
	private ExamSectionHasQuestionDao examSectionHasQuestionDao;
	@Autowired
	private ExamSectionDao examSectionDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamSectionHasQuestionController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamSectionHasQuestion(HttpServletRequest httpServletRequest,
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
			Integer userId = principalVo.getUserId();

			ExamSectionHasQuestionVo examSectionHasQuestionVo = ConverterHttpServletRequestToCustomObject
					.getExamSectionHasQuestionVo(httpServletRequest);
//			LOGGER.info("ExamSectCionQuestion create Input: " + examSectionHasQuestionVo);
			Integer id = examSectionHasQuestionService.createExamSectionHasQuestion(examSectionHasQuestionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_ATTACH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> attachQuestionBank(
			@RequestBody List<ExamSectionHasQuestionVo> examSectionHasQuestionVos, Locale locale, Model model,
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

			Integer userId = principalVo.getUserId();
		//	LOGGER.info("QuestionBanks Attach Input: " + examSectionHasQuestionVos);
			examSectionHasQuestionService.attachBatchExamSectionHasQuestion(examSectionHasQuestionVos, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExamSectionHasQuestion(HttpServletRequest httpServletRequest,
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
			Integer userId = principalVo.getUserId();

			ExamSectionHasQuestionVo examSectionHasQuestionVo = ConverterHttpServletRequestToCustomObject
					.getExamSectionHasQuestionVo(httpServletRequest);
			//LOGGER.info("ExamSectCionQuestion Update Input: " + examSectionHasQuestionVo);
			Integer id = examSectionHasQuestionService.updateExamSectionHasQuestion(examSectionHasQuestionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamSectionHasQuestion(
			@RequestBody Map<String, Integer> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("ExamSectCionQuestion Read Input: " + requestobject);
			Integer userId = principalVo.getUserId();
			Integer examSectionHasQuestionId = requestobject.get("examSectionHasQuestionId");

			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				@SuppressWarnings("unchecked")
				List<Integer> examSectionHasQuestionIdList = (List<Integer>) examSectionHasQuestionDao
						.listSingleRowResult(ExamSectionHasQuestionDao.findESHIDSByUserId, paramsKayAndValues);
				if (examSectionHasQuestionIdList == null
						|| !examSectionHasQuestionIdList.contains(examSectionHasQuestionId)) {
					LOGGER.warn("Access Not Authorized User");
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();

				}
			}

			ExamSectionHasQuestionVo examSectionHasQuestionVo = examSectionHasQuestionService
					.readExamSectionHasQuestion(examSectionHasQuestionId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionHasQuestionVo)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSectionHasQuestion(@RequestBody Map<String, Object> requestobject,
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
			LOGGER.info("ExamSectCionQuestion List Input: " + requestobject);
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

			Map<String, Object> examSectionHasQuestionVoList = examSectionHasQuestionService
					.listExamSectionHasQuestion(pageNo, pageSize, searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success)
					.object(examSectionHasQuestionVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_EXAMSECTIONID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSectionHasQuestionByexamSectionId(
			@RequestBody Map<String, Object> requestobject, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("ExamSectcionQuestion List By SectionId Input: " + requestobject);
			Integer userId = principalVo.getUserId();

			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Integer examSectionId = requestobject.get("examSectionId") != null
					? Integer.parseInt(requestobject.get("examSectionId").toString().trim())
					: null;

			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				@SuppressWarnings("unchecked")
				List<Integer> examSectionIdList = (List<Integer>) examSectionDao
						.listSingleRowResult(ExamSectionDao.findExamSectionIdsByTcId, paramsKayAndValues);
				if (examSectionIdList == null || !examSectionIdList.contains(examSectionId)) {
					LOGGER.warn("Access Not Authorized User");
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();
				}
			}

			Map<String, Object> examSectionHasQuestionVos = examSectionHasQuestionService
					.listExamSectionHasQuestionVoBySectionId(examSectionId, pageNo, pageSize, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionHasQuestionVos)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
