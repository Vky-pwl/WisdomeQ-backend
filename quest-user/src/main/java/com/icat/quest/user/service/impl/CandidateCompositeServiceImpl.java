package com.icat.quest.user.service.impl;

import java.util.*;

import com.icat.quest.utils.TinyLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.dao.PublishExamLicenseDao;
import com.icat.quest.dao.user.service.CandidateService;
import com.icat.quest.model.PublishExamLicense;
import com.icat.quest.model.TestConductor;
import com.icat.quest.service.TestConductorHasTestCodeService;
import com.icat.quest.user.service.CandidateCompositeService;

@Component
public class CandidateCompositeServiceImpl implements CandidateCompositeService {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private PublishExamLicenseDao publishExamLicenseDao;
    @Autowired
    private TestConductorHasTestCodeService testConductorHasTestCodeService;
    @Autowired
    private TinyLinkService tinyLinkService;


    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateCompositeServiceImpl.class);

    @Override
    public Map<String, Object> attachExam(String testConductorLicenseCode, Integer userId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("_1_code", testConductorLicenseCode);
        List<PublishExamLicense> publishExamLicenses = publishExamLicenseDao
                .listEntityByParameter(PublishExamLicenseDao.findByTCLCode, param, null, null);
        if (publishExamLicenses != null && publishExamLicenses.size() > 0) {
            PublishExamLicense publishExamLicense = publishExamLicenses.get(0);
            if (publishExamLicense.getEndTime().after(new Date())) {
                List<Integer> userIdList = new ArrayList<>();
                userIdList.add(userId);
                String tinyKey = null;
                testConductorHasTestCodeService.assignedUserTestCode(userIdList,
                        publishExamLicense.getTestConductorLicense().getTestConductorLicenseId(), userId, false,
                        tinyKey);
                if (tinyKey == null) {
                    LOGGER.info("Already Assigned exam");
                    response.put(Constants.STATUS_ERROR, "Already Assigned exam");
                    return response;
                }
                response.put(Constants.STATUS_SUCCESS, Constants.STATUS_SUCCESS);
                response.put("tinyKey", tinyKey);
                response.put("examId", publishExamLicense.getTestConductorLicense().getExam().getExamId());

            }
        }

        return response;

    }

    @Override
    public Map<String, Object> signupCandidateComposite(CandidateVo candidateVo) throws Exception {
        // Candidate, ExamLicense,
        Map<String, Object> response = new HashMap<>();
        if (candidateVo.getTestConductorLicenseCode() == null) {
            LOGGER.info("TestConductorLicenseCode should not be null: ");
            response.put(Constants.STATUS_ERROR, "TestConductorLicenseCode should not be null");
            return response;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("_1_code", candidateVo.getTestConductorLicenseCode());
        List<PublishExamLicense> publishExamLicenses = publishExamLicenseDao
                .listEntityByParameter(PublishExamLicenseDao.findByTCLCode, param, null, null);
        if (publishExamLicenses != null && publishExamLicenses.size() > 0) {
            PublishExamLicense publishExamLicense = publishExamLicenses.get(0);
            if (publishExamLicense.getEndTime().after(new Date())) {
                TestConductor testConductor = publishExamLicense.getTestConductorLicense().getTestConductor();
                if (testConductor.getCollege() != null) {
                    candidateVo.setCollegeVo(new CollegeVo(testConductor.getCollege().getCollegeId()));
                }
                Map<String, Object> candidateResponse = candidateService.createCandidate(candidateVo, testConductor.getTestConductorId());
                if (candidateResponse.containsKey(Constants.STATUS_ERROR)) {
                    LOGGER.info("CandidateCreate getting error");
                    response.put(Constants.STATUS_ERROR, "CandidateCreate getting error");
                    return response;
                }
                List<Integer> userIdList = new ArrayList<>();
                Integer userId = (Integer) candidateResponse.get(Constants.STATUS_SUCCESS);
                userIdList.add(userId);
                String tinyKey = null;/*tinyLinkService.shortenURL(
                        publishExamLicense.getTestConductorLicense().getTestConductorLicenseId(),
                        userId, Calendar.getInstance().getTimeInMillis());*/
                testConductorHasTestCodeService.assignedUserTestCode(userIdList,
                        publishExamLicense.getTestConductorLicense().getTestConductorLicenseId(), userId, false,
                        tinyKey);
                if (tinyKey == null) {
                    LOGGER.info("Already Assigned exam");
                    response.put(Constants.STATUS_ERROR, "Already Assigned exam");
                    return response;
                }
                response.put(Constants.STATUS_SUCCESS, Constants.STATUS_SUCCESS);
                response.put("tinyKey", tinyKey);
                response.put("examId", publishExamLicense.getTestConductorLicense().getExam().getExamId());

            }
        }
        return response;
    }
}
