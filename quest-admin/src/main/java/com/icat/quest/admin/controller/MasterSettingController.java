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

import com.icat.quest.admin.service.MasterSettingService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.PrincipalParseService;
import com.icat.quest.common.utils.PrincipalVo;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.MasterSettingVo;
import com.icat.quest.common.vo.UserType;

@RestController
@RequestMapping(value = Constants.OPERATION_MASTER_SETTING)
public class MasterSettingController {

	@Autowired
	private MasterSettingService masterSettingService;
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterSettingController.class);

	@RequestMapping(value = Constants.OPERATION_CREATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createMasterSetting(@RequestBody MasterSettingVo masterSettingVo, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("MasterSetting Create Input: " + masterSettingVo);	
			Integer userId = principalVo.getUserId();
			Integer id = masterSettingService.createMasterSetting(masterSettingVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateMasterSetting(@RequestBody MasterSettingVo masterSettingVo, Locale locale,
			Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("MasterSetting Update Input: " + masterSettingVo);	
			Integer userId = principalVo.getUserId();
			Integer id = masterSettingService.updateMasterSetting(masterSettingVo, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).id(id).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_READ, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createMasterSetting(@RequestBody Map<String, Integer> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("MasterSetting Read Input: " + requestobject);	
			Integer userId = principalVo.getUserId();
			Integer masterSettingId = requestobject.get("masterSettingId");
			MasterSettingVo masterSettingVo = masterSettingService.readMasterSetting(masterSettingId, userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(masterSettingVo).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

	@RequestMapping(value = Constants.OPERATION_LIST, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listMasterSetting(@RequestBody Map<String, Object> requestobject,
			Locale locale, Model model, Principal principal) {

		try {
			PrincipalVo principalVo = PrincipalParseService.trimPrincipalToken(principal.getName());
			if (principalVo == null || principalVo.getUserId() == null || !principalVo.getUserType().equals(UserType.SUPERADMIN)) {
				LOGGER.warn("Access Not Authorized User");
				return new ResponseBuilder(false).status(ResponseBuilder.Status.error)
						.message("Access Not Authorized User").build();
			}
			LOGGER.info("LoggedUserId: " + principalVo.getUserId() + "\tUserType: "+principalVo.getUserType());
			LOGGER.info("MasterSetting List Input: " + requestobject);	
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

			Map<String, Object> masterSettingVoList = masterSettingService.listMasterSetting(pageNo, pageSize, searchKey, active,
					userId);
			return new ResponseBuilder(false).status(ResponseBuilder.Status.success).object(masterSettingVoList).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBuilder(false).status(ResponseBuilder.Status.error).message(e.getMessage()).build();

		}
	}

}
