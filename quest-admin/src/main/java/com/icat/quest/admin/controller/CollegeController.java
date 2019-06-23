package com.icat.quest.admin.controller;

import java.security.Principal;
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

import com.icat.quest.admin.service.CollegeService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_COLLEGE)
public class CollegeController {

	@Autowired
	private CollegeService collegeService;
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCollege(HttpServletRequest httpServletRequest,
			@RequestBody CollegeVo collegeVo, Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("College Create Input: " + collegeVo);
			Integer id = collegeService.createCollege(collegeVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateCollege(@RequestBody CollegeVo collegeVo, Locale locale, Model model,
			Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			LOGGER.info("College Update Input: " + collegeVo);

			Integer id = collegeService.updateCollege(collegeVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createCollege(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
			Integer collegeId = requestobject.get("collegeId");
			LOGGER.info("CollegeId Read Input: " + collegeId);
			CollegeVo collegeVo = collegeService.readCollege(collegeId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(collegeVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listCollege(@RequestBody Map<String, Object> requestobject, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null
					|| !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			Integer userId = principalVo.getUserId();
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: " + principalVo.getUserType());
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
			LOGGER.info("CollegeList Input: " + requestobject);
			Map<String, Object> collegeVoList = collegeService.listCollege(pageNo, pageSize, searchKey, active, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(collegeVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
