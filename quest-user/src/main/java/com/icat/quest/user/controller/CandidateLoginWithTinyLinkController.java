package com.icat.quest.user.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.dao.user.service.CandidateService;
import com.icat.quest.service.TestConductorHasTestCodeService;
import com.icat.quest.user.service.CandidateCompositeService;
import com.icat.quest.user.service.CandidateLoginDetailService;

@RestController
@RequestMapping(value = Constants.OPERATION_CANDIDATE_LOGIN)
public class CandidateLoginWithTinyLinkController {

	@Autowired
	private CandidateLoginDetailService candidateLoginDetailService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private CandidateCompositeService candidateCompositeService;
	@Autowired
	private TestConductorHasTestCodeService testConductorHasTestCodeService;

	@RequestMapping(value = Constants.OPERATION_TINYLINK_LOGIN + "/{token}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> CandidateExamDetails(@PathVariable("token") String token, Locale locale,
			Model model) {

		try {
			Map<String, Object> responseMap = candidateLoginDetailService.getCandidateLoginDetails(token, true);
			if (responseMap != null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(responseMap).build();
			} else {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Credential")
						.build();

			}
		} catch (Exception e) {
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_TINYLINK_RESUME + "/{token}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> pauseCandidateExamDetail(@PathVariable("token") String token,
			Locale locale, Model model) {

		try {
			Map<String, Object> responseMap = candidateLoginDetailService.getCandidateLoginDetails(token, true);
			if (responseMap != null) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(responseMap).build();
			} else {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message("Invalid Credential")
						.build();

			}
		} catch (Exception e) {
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_CREATE_BATCH, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createBatchUser(@RequestBody List<CandidateVo> candidateVoList,
			Locale locale, Model model) {

		try {
			return candidateService.createBatchCandidate(candidateVoList, 0001, null).build();
		} catch (Exception e) {
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_SIGNUP, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> signupCandidate(@RequestBody CandidateVo candidateVo, Locale locale,
			Model model) {

		try {
			Map<String, Object> response = candidateCompositeService.signupCandidateComposite(candidateVo);
			if (response.containsKey(Constants.STATUS_SUCCESS)) {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(response).build();
			} else {
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error).object(response).build();
			}
		} catch (Exception e) {
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = "/updateTC", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> updateTC(Locale locale, Model model) {
		testConductorHasTestCodeService.updateTestCodes();
		return new ResponseBuilder(false).status(ResponseBuilder.Status.success).build();
	}

}
