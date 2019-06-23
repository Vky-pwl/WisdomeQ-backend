package com.icat.quest.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.PublishExamLicenseService;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.dao.PublishExamLicenseDao;
import com.icat.quest.dao.TestConductorLicenseDao;
import com.icat.quest.model.PublishExamLicense;
import com.icat.quest.model.TestConductorLicense;

@Service
public class PublishExamLicenseServiceImpl implements PublishExamLicenseService {

	@Autowired
	private PublishExamLicenseDao publishExamLicenseDao;
	@Autowired
	private TestConductorLicenseDao testConductorLicenseDao;

	@Override
	public String publishExamLicense(Integer testConductorLicenseId, Long startTime, Long endTime, Integer userId, Boolean flag) {
		TestConductorLicense testConductorLicense = testConductorLicenseDao.read(testConductorLicenseId);
		
		if (testConductorLicense == null || (flag && !testConductorLicense.getTestConductor().getTestConductorId().equals(userId))) {
			return null;
		}
		deactivePublishExamLicense(testConductorLicenseId);
		Date startDate = startTime == null ? new Date() : new Date(startTime);
		Date endDate = endTime != null ? new Date(endTime) : new Date(new Date().getTime()+(60000*60*24));
		String code = testConductorLicense.getTestConductorCode()+randomString();
		PublishExamLicense publishExamLicense = new PublishExamLicense(testConductorLicense,
				code, startDate, endDate, true, new Date(), new Long(userId),
				new Date(), new Long(userId));
		publishExamLicenseDao.create(publishExamLicense);
		return code;
	}

	@Override
	public void deactivePublishExamLicense(Integer testConductorLicenseId) {
		Map<String, Object> params = new HashMap<>();
		params.put("_1_TCLId", testConductorLicenseId);
		PublishExamLicense publishExamLicense = publishExamLicenseDao
				.findEntityByParameter(PublishExamLicenseDao.findByTCLId, params);
		if(publishExamLicense != null) {
		publishExamLicenseDao.delete(publishExamLicense);
		}
	}

	private String randomString() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
	
	@Override
	public Map<String,Object> getPublishLicense(Integer testConductorLicenseId){
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("_1_TCLId", testConductorLicenseId);
		PublishExamLicense publishExamLicense = publishExamLicenseDao
				.findEntityByParameter(PublishExamLicenseDao.findByTCLId, params);
		if(publishExamLicense == null || publishExamLicense.getEndTime().before(new Date())) {
			response.put(Constants.STATUS_ERROR, "No LicenseKey available");
		}
		response.put("testConductorLicenseCode", publishExamLicense.getTestConductorLicenseCode());
		response.put("startTime", publishExamLicense.getStartTime().getTime());
		response.put("endTime", publishExamLicense.getEndTime().getTime());
		
		return response;
		
	}

}
