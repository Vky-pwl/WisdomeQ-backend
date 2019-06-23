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
import com.icat.quest.common.vo.CandidateResultVo;
import com.icat.quest.common.vo.EAIResultVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.service.ExamResultService;
import com.icat.quest.service.UserHasPermissionService;

@RestController
@RequestMapping(value = Constants.OPERATION_EXAM)
public class ExamResultController {

	@Autowired
	private ExamResultService examResultService;
	@Autowired
	private UserHasPermissionService userHasPermissionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamResultController.class);

	@RequestMapping(value = Constants.OPERATION_EXAM_RESULT_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listCandidateExamResultDesc(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| principalVo.getUserType().equals(UserType.CANDIDATE)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());

			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Integer examId = requestobject.get("examId") != null
					? Integer.parseInt(requestobject.get("examId").toString().trim())
					: null;
			Integer collegeId = requestobject.get("collegeId") != null
					? Integer.parseInt(requestobject.get("collegeId").toString().trim())
					: null;
			Integer specilizationId = requestobject.get("specilizationId") != null
					? Integer.parseInt(requestobject.get("specilizationId").toString().trim())
					: null;
			Float percentile = requestobject.get("percentile") != null
					? Float.parseFloat(requestobject.get("percentile").toString().trim())
					: null;
		    Long startDate = requestobject.get("startDate") != null
					? Long.parseLong(requestobject.get("startDate").toString().trim())
							: null;
		    Long endDate = requestobject.get("endDate") != null
								? Long.parseLong(requestobject.get("endDate").toString().trim())
										: null;			
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				collegeId = principalVo.getCollegeId();
			}
			LOGGER.info("CandidateResult Input: " + requestobject);
			Map<String, Object> results = examResultService.getResultListByExamId(examId, pageNo, pageSize, collegeId,
					specilizationId,percentile, startDate, endDate);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(results).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_EXAM_LIST_BY_EXAM_ID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listResultByExamId(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| principalVo.getUserType().equals(UserType.CANDIDATE)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());

			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Integer examId = requestobject.get("examId") != null
					? Integer.parseInt(requestobject.get("examId").toString().trim())
					: null;
			Integer collegeId = requestobject.get("collegeId") != null
					? Integer.parseInt(requestobject.get("collegeId").toString().trim())
					: null;
			Integer specilizationId = requestobject.get("specilizationId") != null
					? Integer.parseInt(requestobject.get("specilizationId").toString().trim())
					: null;
			Long startDate = requestobject.get("startDate") != null
					? Long.parseLong(requestobject.get("startDate").toString().trim())
					: null;
		    Long endDate = requestobject.get("endDate") != null
					? Long.parseLong(requestobject.get("endDate").toString().trim())
					: null;			
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				collegeId = principalVo.getCollegeId();
			}
			LOGGER.info("CandidateResult Input: " + requestobject);
			Map<String, Object> results = examResultService.getResultsByExamId(examId, pageNo, pageSize, collegeId,
					specilizationId, startDate, endDate);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(results).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_EXAM_LIST_TOTAL_ATTEMPT, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listExamTotalAttempts(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| principalVo.getUserType().equals(UserType.CANDIDATE)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());

			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Integer collegeId = requestobject.get("collegeId") != null
					? Integer.parseInt(requestobject.get("collegeId").toString().trim())
					: null;
			Integer specilizationId = requestobject.get("specilizationId") != null
					? Integer.parseInt(requestobject.get("specilizationId").toString().trim())
					: null;
			
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				collegeId = principalVo.getCollegeId();
			}
			LOGGER.info("Exam List Total Attempt Input: " + requestobject + "/tCollegeId: " + collegeId);
			Map<String, Object> results = examResultService.getExamTotalAttemps(pageNo, pageSize, collegeId,
					specilizationId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(results).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_EXAM_LIST_RANK, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> examListRank(@RequestBody Map<String, Object> requestobject, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());

			Integer pageNo = requestobject.get("pageNo") != null
					? Integer.parseInt(requestobject.get("pageNo").toString().trim())
					: null;
			Integer pageSize = requestobject.get("pageSize") != null
					? Integer.parseInt(requestobject.get("pageSize").toString().trim())
					: null;
			Integer collegeId = requestobject.get("collegeId") != null
					? Integer.parseInt(requestobject.get("collegeId").toString().trim())
					: null;
			Integer specilizationId = requestobject.get("specilizationId") != null
					? Integer.parseInt(requestobject.get("specilizationId").toString().trim())
					: null;
			Integer candidateId = requestobject.get("candidateId") != null
					? Integer.parseInt(requestobject.get("candidateId").toString().trim())
					: null;
			Float percentile = requestobject.get("percentile") != null
							? Float.parseFloat(requestobject.get("percentile").toString().trim())
							: null;
					
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				collegeId = principalVo.getCollegeId();
			}
			if (principalVo.getUserType().equals(UserType.CANDIDATE)) {
				candidateId = principalVo.getUserId();
			}
			LOGGER.info("Exam List Rank Input: " + requestobject + "/tCandidateId: " + candidateId + "/tCollegeId: "
					+ collegeId);

			Map<String, Object> results = examResultService.getCandRankExamList(pageSize, pageNo, collegeId,
					candidateId, specilizationId, percentile);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(results).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CAND_RESULT_BY_EXAMID, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getResults(@RequestBody Map<String, Integer> requestobject, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer candidateId = requestobject.get("candidateId");
			Integer testConductorHasTestCodeId = requestobject.get("testConductorHasTestCodeId");
			if (principalVo.getUserType().equals(UserType.CANDIDATE)) {
				candidateId = principalVo.getUserId();
			}
			LOGGER.info("CandidateResult By ExamId Input: " + requestobject + "/tCandidateId: " + candidateId);

			CandidateResultVo candidateResultVo = examResultService.getCertificate(testConductorHasTestCodeId, candidateId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(candidateResultVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();
		}
	}

	@RequestMapping(value = Constants.OPERATION_CERTIFICATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCertificate(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer candidateId = requestobject.get("candidateId");
			Integer testConductorHasTestCodeId = requestobject.get("testConductorHasTestCodeId");
			Integer examId = requestobject.get("examId");
			if (principalVo.getUserType().equals(UserType.CANDIDATE)) {
				candidateId = principalVo.getUserId();
			}
			LOGGER.info("Get certificate Input: " + requestobject + "/tCandidateId: " + candidateId);
			boolean isEligble = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				isEligble = userHasPermissionService.isPermission(principalVo.getUserId(), examId,
						principalVo.getUserType(), 1);
				if (!isEligble && principalVo.getCollegeId() != null) {
					isEligble = userHasPermissionService.isPermissionTemp(principalVo.getCollegeId(), testConductorHasTestCodeId, 1);
				}
				if (!isEligble) {
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("User have no permission for Get Certifcate ").build();
				}
			}
			CandidateResultVo candidateResultVo = examResultService.getCertificate(testConductorHasTestCodeId, candidateId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(candidateResultVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_EAI_CERTIFICATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getEAICertificate(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer candidateId = requestobject.get("candidateId");
			Integer examId = requestobject.get("examId");
			Integer testConductorHasTestCodeId = requestobject.get("testConductorHasTestCodeId");
			
			if (principalVo.getUserType().equals(UserType.CANDIDATE)) {
				candidateId = principalVo.getUserId();
			}
			LOGGER.info("Get EAIcertificate Input: " + requestobject + "/tCandidateId: " + candidateId);

			boolean isEligble = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				isEligble = userHasPermissionService.isPermission(principalVo.getUserId(), examId,
						principalVo.getUserType(), 7);
				if (!isEligble && principalVo.getCollegeId() != null) {
					isEligble = userHasPermissionService.isPermissionTemp(principalVo.getCollegeId(), examId, 7);
				}
				if (!isEligble) {
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("User have no permission for Get EAI Certifcate ").build();
				}
			}
			List<EAIResultVo> eaiResultVos = examResultService.getEAIResult(testConductorHasTestCodeId, candidateId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).list(eaiResultVos).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
	
	@RequestMapping(value = Constants.OPERATION_EAI_SUMMARY, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getEAISummary(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer candidateId = requestobject.get("candidateId");
			Integer examId = requestobject.get("examId");
			Integer testConductorHasTestCodeId = requestobject.get("testConductorHasTestCodeId");
			
			if (principalVo.getUserType().equals(UserType.CANDIDATE)) {
				candidateId = principalVo.getUserId();
			}
			LOGGER.info("Get EAIcertificate Input: " + requestobject + "/tCandidateId: " + candidateId);

			boolean isEligble = false;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				isEligble = userHasPermissionService.isPermission(principalVo.getUserId(), examId,
						principalVo.getUserType(), 7);
				if (!isEligble && principalVo.getCollegeId() != null) {
					isEligble = userHasPermissionService.isPermissionTemp(principalVo.getCollegeId(), examId, 7);
				}
				if (!isEligble) {
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("User have no permission for Get EAI Certifcate ").build();
				}
			}
			Map<String, String> summary = examResultService.getSkillAssestment(testConductorHasTestCodeId, candidateId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(summary).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}
}
