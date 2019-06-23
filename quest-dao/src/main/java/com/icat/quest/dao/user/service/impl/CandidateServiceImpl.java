/**
 * 
 */
package com.icat.quest.dao.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.ResponseBuilder;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.GenderType;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.CollegeDao;
import com.icat.quest.dao.SpecializationDao;
import com.icat.quest.dao.user.service.CandidateService;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.College;
import com.icat.quest.model.Specialization;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private CollegeDao collegeDao;
	@Autowired
	private SpecializationDao specializationDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Override
	public ResponseBuilder createBatchCandidate(List<CandidateVo> candidateVoList, Integer userId, Integer collegeId) {
		ResponseBuilder responseBuilder = new ResponseBuilder(false);
		if (candidateVoList == null || candidateVoList.isEmpty()) {
			LOGGER.info("Input is not valid: " + candidateVoList);
			return responseBuilder.status(ResponseBuilder.Status.error)
					.message("Input is not valid:");
		}
		College college = null;
		if (collegeId != null) {
			college = collegeDao.read(collegeId);
		}
		List<Candidate> candidateList = new ArrayList<>();
		for (CandidateVo candidateVo : candidateVoList) {
			if (candidateVo == null) {
				LOGGER.info("Input is not valid: " + candidateVo);
				continue;
			}
			
			if (candidateVo.getContactEmail() == null && candidateVo.getContactNumber() == null) {
				LOGGER.info("ContactNumber or ContactEmail should not be null: " + candidateVo);
				continue;
			}

			Specialization specialization = null;
			
			if (candidateVo.getSpecializationVo() != null
					&& candidateVo.getSpecializationVo().getSpecializationId() != null) {
				specialization = specializationDao.read(candidateVo.getSpecializationVo().getSpecializationId());
			}
			Candidate candidate = new Candidate();
			candidate.setCollege(college);
			candidate.setSpecialization(specialization);
			candidate.setFirstName(candidateVo.getFirstName());
			candidate.setMiddleName(candidateVo.getMiddleName());
			candidate.setLastName(candidateVo.getLastName());
			candidate.setGender(GenderType.valueOf(candidateVo.getGender()));
			if(candidateVo.getDateOfBirth() != null) {
			candidate.setDateOfBirth(new Date(candidateVo.getDateOfBirth()));
			}
			candidate.setActive(true);
			candidate.setAddressLine1(candidateVo.getAddressLine1());
			candidate.setAddressLine2(candidateVo.getAddressLine2());
			candidate.setCity(candidateVo.getCity());
			candidate.setState(candidateVo.getState());
			candidate.setCountry(candidateVo.getCountry());
			candidate.setPinCode(candidateVo.getPinCode());

			candidate.setAdhaarNumber(candidateVo.getAdhaarNumber());
			candidate.setTenthPercentage(candidateVo.getTenthPercentage());
			candidate.setContactEmail(candidateVo.getContactEmail());
			candidate.setPassword(candidateVo.getPassword());
			candidate.setContactNumber(candidateVo.getContactNumber());

			candidate.setCreated(new Date());
			candidate.setCreatedBy(new Long(userId));
			candidate.setLastModified(new Date());
			candidate.setLastModifiedBy(new Long(userId));
			candidateList.add(candidate);
		}
		if(candidateList.size()>0) {
		List<Integer> ids = candidateDao.createBatch(candidateList);
		LOGGER.info("Candidates created id: " + ids);
		return responseBuilder.status(ResponseBuilder.Status.success).idList(ids);
		}
		return responseBuilder.status(ResponseBuilder.Status.error).message("Data not inserted");
	}

	@Override
	public Map<String, Object> createCandidate(CandidateVo candidateVo, Integer userId) {
		Map<String,Object> responseMap = new HashMap<>();
		if (candidateVo == null) {
			LOGGER.info("Input is not valid: " + candidateVo);
			responseMap.put(Constants.STATUS_ERROR,"Input is not valid");
			return responseMap;
		}
		
		if (candidateVo.getContactEmail() == null && candidateVo.getContactNumber() == null) {
			LOGGER.info("ContactNumber or ContactEmail should not be null: " + candidateVo);
			responseMap.put(Constants.STATUS_ERROR,"ContactNumber and ContactEmail should not be null");
			return responseMap;
		}

		College college = null;
		Specialization specialization = null;
		if (candidateVo.getCollegeVo() != null && candidateVo.getCollegeVo().getCollegeId() != null) {
			college = collegeDao.read(candidateVo.getCollegeVo().getCollegeId());
		}
		if (candidateVo.getSpecializationVo() != null
				&& candidateVo.getSpecializationVo().getSpecializationId() != null) {
			specialization = specializationDao.read(candidateVo.getSpecializationVo().getSpecializationId());
		}
		Date dateOfBirth = null;
		if (candidateVo.getDateOfBirth() != null) {
			dateOfBirth = new Date(candidateVo.getDateOfBirth());
		}
		Candidate candidate = new Candidate();
		candidate.setCollege(college);
		candidate.setSpecialization(specialization);
		candidate.setFirstName(candidateVo.getFirstName());
		candidate.setMiddleName(candidateVo.getMiddleName());
		candidate.setLastName(candidateVo.getLastName());
		candidate.setGender(GenderType.valueOf(candidateVo.getGender()));
		candidate.setDateOfBirth(dateOfBirth);

		candidate.setActive(true);
		candidate.setAddressLine1(candidateVo.getAddressLine1());
		candidate.setAddressLine2(candidateVo.getAddressLine2());
		candidate.setCity(candidateVo.getCity());
		candidate.setState(candidateVo.getState());
		candidate.setCountry(candidateVo.getCountry());
		candidate.setPinCode(candidateVo.getPinCode());

		candidate.setAdhaarNumber(candidateVo.getAdhaarNumber());
		candidate.setTenthPercentage(candidateVo.getTenthPercentage());
		candidate.setContactEmail(candidateVo.getContactEmail());
		candidate.setPassword(candidateVo.getPassword());
		candidate.setContactNumber(candidateVo.getContactNumber());

		candidate.setCreated(new Date());
		candidate.setCreatedBy(new Long(userId));
		candidate.setLastModified(new Date());
		candidate.setLastModifiedBy(new Long(userId));

		Integer id = candidateDao.create(candidate);
		LOGGER.info("Candidate created id: " + id);
		responseMap.put(Constants.STATUS_SUCCESS,id);
		return responseMap;
	}

	@Override
	public Integer updateCandidate(CandidateVo candidateVo, Integer userId) throws Exception {
		if (candidateVo.getUserId() == null)
			throw new Exception("UserId should not be null");

		Candidate candidate = candidateDao.read(candidateVo.getUserId());
		if (candidate == null)
			throw new Exception("Record not found");

		if (candidateVo.getCollegeVo() != null && candidateVo.getCollegeVo().getCollegeId() != null
				&& (candidate.getCollege() == null
						|| !candidate.getCollege().getCollegeId().equals(candidateVo.getCollegeVo().getCollegeId()))) {
			candidate.setCollege(collegeDao.read(candidateVo.getCollegeVo().getCollegeId()));
		}
		if (candidateVo.getSpecializationVo() != null && candidateVo.getSpecializationVo().getSpecializationId() != null
				&& (candidate.getSpecialization() == null || !candidate.getSpecialization().getSpecializationId()
						.equals(candidateVo.getSpecializationVo().getSpecializationId()))) {
			candidate
					.setSpecialization(specializationDao.read(candidateVo.getSpecializationVo().getSpecializationId()));
		}

		if (candidateVo.getFirstName() != null) {
			candidate.setFirstName(candidateVo.getFirstName().trim());
		}
		if (candidateVo.getMiddleName() != null) {
			candidate.setMiddleName(candidateVo.getMiddleName().trim());
		}
		if (candidateVo.getLastName() != null) {
			candidate.setLastName(candidateVo.getLastName().trim());
		}
		if (candidateVo.getGender() != null) {
			candidate.setGender(GenderType.valueOf(candidateVo.getGender()));
		}
		if (candidateVo.getDateOfBirth() != null) {
			candidate.setDateOfBirth(new Date(candidateVo.getDateOfBirth()));
		}

		if (candidateVo.getAdhaarNumber() != null) {
			candidate.setAdhaarNumber(candidateVo.getAdhaarNumber().trim());
		}
		if (candidateVo.getTenthPercentage() != null) {
			candidate.setTenthPercentage(candidateVo.getTenthPercentage().trim());
		}
		if (candidateVo.getContactEmail() != null) {
			candidate.setContactEmail(candidateVo.getContactEmail().trim());
		}
		if (candidateVo.getContactNumber() != null) {
			candidate.setContactNumber(candidateVo.getContactNumber().trim());
		}
		if (candidateVo.getPassword() != null) {
			candidate.setPassword(candidateVo.getPassword().trim());
		}

		if (candidateVo.getAddressLine1() != null) {
			candidate.setAddressLine1(candidateVo.getAddressLine1().trim());
		}
		if (candidateVo.getAddressLine2() != null) {
			candidate.setAddressLine2(candidateVo.getAddressLine2().trim());
		}
		if (candidateVo.getCity() != null) {
			candidate.setCity(candidateVo.getCity().trim());
		}
		if (candidateVo.getState() != null) {
			candidate.setState(candidateVo.getState().trim());
		}
		if (candidateVo.getCountry() != null) {
			candidate.setCountry(candidateVo.getCountry().trim());
		}
		if (candidateVo.getPinCode() != null) {
			candidate.setPinCode(candidateVo.getPinCode().trim());
		}
		if (candidateVo.getActive() != null) {
			candidate.setActive(candidateVo.getActive());
		}
		candidate.setLastModified(new Date());
		candidate.setLastModifiedBy(new Long(userId));
		candidateDao.update(candidate);
		return candidateVo.getUserId();
	}

	@Override
	public CandidateVo readCandidate(Integer candidateId) throws Exception {
		if (candidateId == null) {
			throw new Exception("userId should not be null");
		}
		Candidate candidate = candidateDao.read(candidateId);
		if (candidate == null) {
			throw new Exception("Record not found");
		}
		return Transformer.CANDIDATE_TRANSFORMER.transform(candidate);
	}

	@Override
	public Map<String, Object> listCandidate(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, Integer collegeId, Integer testConductorLicenseId, Integer testConductorId) throws Exception {

		Map<String, Object> candidateResultSet = new HashMap<>();
		List<Candidate> candidateList = new ArrayList<>();
		if (pageNo == null) {
			pageNo = 50;
		}
		if (pageSize == null) {
			pageSize = 1;
		}
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = (pageNo * pageSize) - pageSize;

		if (searchKey != null) {
			searchKey = searchKey + "%";
		}

		parameters.put("_3_collegeId", collegeId);
		parameters.put("_1_active", active);
		parameters.put("_2_userName", searchKey);
		parameters.put("_4_testConductorLicenseId", testConductorLicenseId);
		parameters.put("_5_testConductorId", testConductorId);
		@SuppressWarnings("unchecked")
		List<Integer> userIdList = (List<Integer>) candidateDao.listSingleRowResult(CandidateDao.userIdListByCriteria,
				parameters);

		if (pageNo == 1) {
			candidateList = candidateDao.listEntityByIdList(userIdList);
			candidateResultSet.put("count", candidateList.size());
			List<CandidateVo> candidateVos = new ArrayList<>();
			candidateList.stream().skip(startIndex).limit(pageSize).forEach(candidate -> {
				candidateVos.add(Transformer.CANDIDATE_TRANSFORMER.transform(candidate));
			});
			candidateResultSet.put("userVoList", candidateVos);
		} else {
			candidateList = candidateDao.listEntityByIdList(
					userIdList.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList()));
			List<CandidateVo> candidateVos = new ArrayList<>();
			candidateList.forEach(candidate -> {
				candidateVos.add(Transformer.CANDIDATE_TRANSFORMER.transform(candidate));
			});
			candidateResultSet.put("userVoList", candidateVos);
		}
		return candidateResultSet;
	}

}
