package com.icat.quest.admin.service;

import java.util.Map;

public interface PublishExamLicenseService {

	void deactivePublishExamLicense(Integer testConductorLicenseId);

	String publishExamLicense(Integer testConductorLicenseId, Long startTime, Long endTime, Integer userId,
			Boolean flag);

	Map<String, Object> getPublishLicense(Integer testConductorLicenseId);

}
