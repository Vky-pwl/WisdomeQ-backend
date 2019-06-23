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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icat.quest.admin.service.ExamSectionService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamDao;
import com.icat.quest.dao.ExamSectionDao;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM_SECTION)
public class ExamSectionController {

	@Autowired
	private ExamSectionService examSectionService;
	@Autowired
	private ExamSectionDao examSectionDao;
	@Autowired
	private ExamDao examDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamSectionController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamSection(@RequestBody ExamSectionVo examSectionVo, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("ExamSection Create Input: "+examSectionVo);
			
			Integer id = examSectionService.createExamSection(examSectionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateExamSection(@RequestBody ExamSectionVo examSectionVo, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
			LOGGER.info("ExamSection Update Input: "+examSectionVo);
			Integer id = examSectionService.updateExamSection(examSectionVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createExamSection(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();

			Integer examSectionId = requestobject.get("examSectionId");
			LOGGER.info("Read SecionId : "+requestobject);
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				@SuppressWarnings("unchecked")
				List<Integer> examSectionIdList = (List<Integer>) examSectionDao
						.listSingleRowResult(ExamSectionDao.findExamSectionIdsByTcId, paramsKayAndValues);
				if (examSectionIdList == null || !examSectionIdList.contains(examSectionId)) {
					LOGGER.info("User not Authorized");
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();
				}
			}

			ExamSectionVo examSectionVo = examSectionService.readExamSection(examSectionId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSection(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
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
			LOGGER.info("ExamSection List: "+requestobject);
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				@SuppressWarnings("unchecked")
				List<Integer> examSectionIdList = (List<Integer>) examSectionDao
						.listSingleRowResult(ExamSectionDao.findExamSectionIdsByTcId, paramsKayAndValues);
				Map<String, Object> examVoList = examSectionService.listExamSectionByExamSectionIdList(pageNo, pageSize,
						searchKey, active, userId, examSectionIdList);
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examVoList).build();

			}

			Map<String, Object> examSectionVoList = examSectionService.listExamSection(pageNo, pageSize, searchKey,
					active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST_BY_EXAMID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSectionByExamId(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();

			Integer examId = requestobject.get("examId") != null
					? Integer.parseInt(requestobject.get("examId").toString().trim())
					: null;

			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
				paramsKayAndValues.put("_1_testConductorId", userId);
				@SuppressWarnings("unchecked")
				List<Integer> examIdList = (List<Integer>) examDao.listSingleRowResult(ExamDao.findExamIdsByTcId,
						paramsKayAndValues);
				if (examIdList == null || !examIdList.contains(examId)) {
					LOGGER.warn("Access Not Authorized User");
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();
				}
			}
			LOGGER.info("SectionList By ExamId: "+requestobject);
			List<ExamSectionVo> examSectionVoList = examSectionService.getExamSectionVoListByExamId(examId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(examSectionVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_SUB_SECTION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamSubSection(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer userId = principalVo.getUserId();
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
			LOGGER.info("ExamSubSection List By SectionId: "+requestobject);
			List<QuestionCategoryVo> questionCategoryVos = examSectionService.questionSubCategoryVos(examSectionId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(questionCategoryVos).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
