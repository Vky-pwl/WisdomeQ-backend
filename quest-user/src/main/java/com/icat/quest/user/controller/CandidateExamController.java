package com.icat.quest.user.controller;

import java.security.Principal;
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

import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.CandidateExamVo;
import com.icat.quest.common.vo.CandidateQuestionStatusVo;
import com.icat.quest.common.vo.ExamMetadatDashboardVo;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.ExamStatus;
import com.icat.quest.common.vo.ExamStatusVo;
import com.icat.quest.dao.user.service.CandidateExamService;
import com.icat.quest.dao.user.service.CandidateQuestionStatusService;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;
import com.icat.quest.dao.user.service.TestConductorHasTestCodeUserService;
import com.icat.quest.user.service.CandidateCompositeService;

@RestController
@RequestMapping(value = Constants.OPERATION_CANDIDATE_EXAM)
public class CandidateExamController {

	@Autowired
	private CandidateExamService candidateExamService;
	@Autowired
	private TestConductorHasTestCodeUserService	testConductorHasTestCodeUserService;
	@Autowired
	private ExamQuestionCacheService		examQuestionCacheService;
	@Autowired
	private CandidateQuestionStatusService candidateQuestionStatusService;
	@Autowired
	private CandidateCompositeService	candidateCompositeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateExamController.class);

	

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCandidateExam(@RequestBody CandidateExamVo candidateExamVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			candidateExamVo.setCandidateId(principalVo.getUserId());
			LOGGER.info("submit Answer: "+candidateExamVo);
			candidateExamService.createCandidateExam(candidateExamVo);
			ExamSectionHasQuestionVo examSectionHasQuestionVo =  examQuestionCacheService.getQuestion(principalVo.getUserId(), candidateExamVo.getExamId(), candidateExamVo.getRemainingTime(), candidateExamVo.getSectionRemainingTime(), true,candidateExamVo.getExamSectionHasQuestionId(), candidateExamVo.getTestConductorHasTestCodeId());
			
			if(examSectionHasQuestionVo == null) {
				ExamStatusVo examStatusVo = examQuestionCacheService.getExamStatus(candidateExamVo.getTestConductorHasTestCodeId());
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examStatusVo).build();				
			}
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionHasQuestionVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getExamListByCandidateId(@RequestBody Map<String, Object> requestBody,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			Integer pageNo = (Integer) requestBody.get("pageNo");
			Integer pageSize = (Integer) requestBody.get("pageSize");
			Boolean attended = (Boolean) requestBody.get("attended");
			Map<String, Object> examsResult = candidateExamService.getExamListByCandidateId(principalVo.getUserId(),
					pageNo, pageSize, attended, principalVo.getUserType());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examsResult).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_METADATA, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getExamMetaDataDashboard(Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			
			ExamMetadatDashboardVo examMetadatDashboardVo = candidateExamService.getExamMetadatDashboardVo(principalVo.getUserId());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examMetadatDashboardVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_SUBMIT+"/{testConductorHasTestCodeId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> submitExam(@PathVariable("testConductorHasTestCodeId") Integer testConductorHasTestCodeId,Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			LOGGER.info("Submit Exam");
			testConductorHasTestCodeUserService.submitExam(testConductorHasTestCodeId, principalVo.getUserId());;
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_STATUS_CHANGE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> pauseExam(@RequestBody ExamStatusVo examStatusVo,Locale locale, Model model, Principal principal) {

		try {
			if(examStatusVo == null || examStatusVo.getCurrentExamId() == null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Input").build();		
			}
			LOGGER.info("Status change: "+examStatusVo);
			examQuestionCacheService.updateExamStatus(examStatusVo);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

		
	@RequestMapping(value = Constants.OPERATION_GET_QUESTION, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getQuestionByExamSectionIdAndQuestionId(@RequestBody Map<String, Object> requestMap,Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			Integer examId = (Integer) requestMap.get("examId");
			Integer testConductorHasTestCodeId = (Integer) requestMap.get("testConductorHasTestCodeId");
			Long examRemainingTime =  requestMap.get("examRemainingTime") != null ? Long.parseLong(requestMap.get("examRemainingTime").toString()): null;
			Long sectionRemainingTime =  requestMap.get("sectionRemainingTime") != null ? Long.parseLong(requestMap.get("sectionRemainingTime").toString()): null;
			ExamSectionHasQuestionVo examSectionHasQuestionVo =  examQuestionCacheService.getQuestion(principalVo.getUserId(), examId, examRemainingTime, sectionRemainingTime, false, null,testConductorHasTestCodeId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionHasQuestionVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = "/status"+"/{testConductorHasTestCodeId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getExamStatus(@PathVariable("testConductorHasTestCodeId") Integer testConductorHasTestCodeId,Locale locale, Model model, Principal principal) {

		try {
			ExamStatusVo examStatusVo =  examQuestionCacheService.getExamStatus(testConductorHasTestCodeId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examStatusVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_GET_QUESTION_BY_QUESTION_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getQuestionByQuestionId(@RequestBody Map<String, Object> requestMap,Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			Integer examSectionHasQuestionId = (Integer) requestMap.get("examSectionHasQuestionId");
			Integer examId = (Integer) requestMap.get("examId");
			Integer testConductorHasTestCodeId = (Integer) requestMap.get("testConductorHasTestCodeId");
			Long examRemainingTime =  requestMap.get("examRemainingTime") != null ? Long.parseLong(requestMap.get("examRemainingTime").toString()): null;
			Long sectionRemainingTime =  requestMap.get("sectionRemainingTime") != null ? Long.parseLong(requestMap.get("sectionRemainingTime").toString()): null;
			ExamSectionHasQuestionVo examSectionHasQuestionVo =  examQuestionCacheService.getQuestionAndUpdateStatus(examId, principalVo.getUserId(), examSectionHasQuestionId, examRemainingTime, sectionRemainingTime,testConductorHasTestCodeId);
			//LOGGER.info("Get Question: "+examSectionHasQuestionVo);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examSectionHasQuestionVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_GET_QUESTION_LIST+"/{examId}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getQuestionListByExamId(@PathVariable("examId") Integer examId,Locale locale, Model model, Principal principal) {

		try {
			List<ExamSectionHasQuestionVo> examSectionHasQuestionVos =  examQuestionCacheService.getQuestionListByExamId(examId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(examSectionHasQuestionVos).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_SUBMIT_ANSWER, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> submitAnswer(@RequestBody CandidateExamVo candidateExamVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			candidateExamVo.setCandidateId(principalVo.getUserId());
			candidateExamService.createCandidateExam(candidateExamVo);
			
			ExamStatusVo examStatusVo = examQuestionCacheService.getExamStatus(candidateExamVo.getTestConductorHasTestCodeId());
			examStatusVo.setUpdateFlag(false);
			examStatusVo.setCurrentSectionRemaingTime(candidateExamVo.getSectionRemainingTime());
			examStatusVo.setCurrentQuestionStatus(ExamStatus.INPROGRESS);
			examStatusVo.setCurrentSectionStatus(ExamStatus.INPROGRESS);
			examStatusVo.setCurrentExamStatus(ExamStatus.INPROGRESS);
			examStatusVo.setExamRemaingTime(candidateExamVo.getRemainingTime());
			
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(examStatusVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_QUESTION_STATUS_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> questionStatusUpdate(@RequestBody CandidateQuestionStatusVo candidateQuestionStatusVo,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			candidateQuestionStatusVo.setCandidateId(principalVo.getUserId());

			candidateQuestionStatusService.update(candidateQuestionStatusVo);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_ATTACH+"/{testCode}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> attachExam(@PathVariable("testCode") String testCode,Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			Map<String, Object> response = candidateCompositeService.attachExam(testCode, principalVo.getUserId());
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(response).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
}
