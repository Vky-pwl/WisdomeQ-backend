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
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.user.service.CandidateService;

@RestController
@RequestMapping(value = Constants.OPERATION_USER)
public class CandidateController {

	@Autowired
	private CandidateService candidateService;
	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCandidate(@RequestBody CandidateVo candidateVo, Locale locale,
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
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				if (principalVo.getCollegeId() == null) {
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();
				}
				candidateVo.setCollegeVo(new CollegeVo(principalVo.getCollegeId()));

			}
			LOGGER.info("Candidate Create Input: " + candidateVo);
			Map<String,Object> response = candidateService.createCandidate(candidateVo, userId);
			if(response.containsKey(Constants.STATUS_SUCCESS)) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(response.get(Constants.STATUS_SUCCESS)).build();
			} else {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message((String)response.get(Constants.STATUS_ERROR)).build();	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateCandidate(@RequestBody CandidateVo candidateVo, Locale locale,
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
			
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN) && (principalVo.getCollegeId() == null
					|| !principalVo.getCollegeId().equals(candidateVo.getCollegeVo().getCollegeId()))) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("Candidate Update Input: " + candidateVo);
			Integer id = candidateService.updateCandidate(candidateVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCandidate(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = requestobject.get("userId");
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("Input Id: " + userId);
			CandidateVo candidateVo = candidateService.readCandidate(userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(candidateVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listCandidate(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

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

			Integer collegeId = (Integer) requestobject.get("collegeId");
			Integer testConductorLicenseId = (Integer) requestobject.get("testConductorLicenseId");
			Integer testConductorId = (Integer) requestobject.get("testConductorId");
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				collegeId = principalVo.getCollegeId();
			}
			LOGGER.info("Candidate List Input: " + requestobject + " CollegeId: "+ collegeId+" TestConductorLicenseId: "+testConductorLicenseId);
			Map<String, Object> candidateVoList = candidateService.listCandidate(pageNo, pageSize, searchKey, active,
					userId, collegeId, testConductorLicenseId,testConductorId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(candidateVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CREATE_BATCH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createBatchUser(@RequestBody List<CandidateVo> candidateVoList,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			Integer collegeId = null;
			if (!principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				if (principalVo.getCollegeId() == null) {
					return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
							.message("Access Not Authorized User").build();
				}
				collegeId = principalVo.getCollegeId();
			} else {
				collegeId = candidateVoList.get(0).getCollegeVo() != null
						? candidateVoList.get(0).getCollegeVo().getCollegeId()
						: null;
			}
			LOGGER.info("Candidate CreateBatch Input: " + candidateVoList + " CollegeId: "+ collegeId);
			return candidateService.createBatchCandidate(candidateVoList, userId, collegeId).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
