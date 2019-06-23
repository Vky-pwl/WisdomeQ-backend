/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.TestConductorService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.TestConductorVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.CollegeDao;
import com.icat.quest.dao.TestConductorDao;
import com.icat.quest.model.College;
import com.icat.quest.model.TestConductor;

@Service
public class TestConductorServiceImpl implements TestConductorService {

	@Autowired
	private TestConductorDao testConductorDao;
	@Autowired
	private CollegeDao collageDao;

	@Override
	public Integer craeteTestConductor(TestConductorVo testConductorVo, Integer userId) throws Exception {
		if (testConductorVo == null)
			throw new Exception("Data is not valid");
		College collage = null;
		if (testConductorVo.getCollegeVo() != null && testConductorVo.getCollegeVo().getCollegeId() != null)
			collage = collageDao.read(testConductorVo.getCollegeVo().getCollegeId());

		TestConductor testConductor = new TestConductor();
		testConductor.setCollege(collage);
		testConductor.setFirstName(testConductorVo.getFirstName());
		testConductor.setLastName(testConductorVo.getLastName());
		testConductor.setActive(true);
		testConductor.setContactEmail(testConductorVo.getContactEmail());
		testConductor.setPassword(testConductorVo.getPassword());
		testConductor.setAdminType(testConductorVo.getAdminType());
		testConductor.setParentTestConductorId(testConductorVo.getParentTestConductorId());
		testConductor.setCreated(new Date());
		testConductor.setCreatedBy(new Long(userId));
		testConductor.setLastModified(new Date());
		testConductor.setLastModifiedBy(new Long(userId));

		return testConductorDao.create(testConductor);
	}

	@Override
	public Integer updateTestConductor(TestConductorVo testConductorVo, Integer userId) throws Exception {
		if (testConductorVo.getTestConductorId() == null)
			throw new Exception("TestConductorId should not be null");

		TestConductor testConductor = testConductorDao.read(testConductorVo.getTestConductorId());
		if (testConductor == null)
			throw new Exception("Record not found");

		if (testConductorVo.getCollegeVo() != null && testConductorVo.getCollegeVo().getCollegeId() != null
				&& (testConductor.getCollege() == null || !testConductorVo.getCollegeVo().getCollegeId()
						.equals(testConductor.getCollege().getCollegeId()))) {
			College collage = collageDao.read(testConductorVo.getCollegeVo().getCollegeId());
			testConductor.setCollege(collage);
		}

		if (testConductorVo.getFirstName() != null) {
			testConductor.setFirstName(testConductorVo.getFirstName().trim());
		}
		if (testConductorVo.getLastName() != null) {
			testConductor.setLastName(testConductorVo.getLastName().trim());
		}

		if (testConductorVo.getContactEmail() != null) {
			testConductor.setContactEmail(testConductorVo.getContactEmail().trim());
		}

		if (testConductorVo.getPassword() != null) {
			testConductor.setPassword(testConductorVo.getPassword().trim());
		}

		if (testConductorVo.getAdminType() != null) {
			testConductor.setAdminType(testConductorVo.getAdminType());
		}

		if (testConductorVo.getActive() != null) {
			testConductor.setActive(testConductorVo.getActive());
		}
		if (testConductorVo.getParentTestConductorId() != null) {
			testConductor.setParentTestConductorId(testConductorVo.getParentTestConductorId());
		}
		testConductor.setLastModified(new Date());
		testConductor.setLastModifiedBy(new Long(userId));

		testConductorDao.update(testConductor);
		return testConductorVo.getTestConductorId();
	}

	@Override
	public TestConductorVo readTestConductor(Integer testConductorId, Integer userId) throws Exception {
		if (testConductorId == null) {
			throw new Exception("testConductorId should not be null");
		}
		TestConductor testConductor = testConductorDao.read(testConductorId);
		if (testConductor == null) {
			throw new Exception("Record not found");
		}

		return Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor);
	}

	@Override
	public Map<String, Object> listTestConductor(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, String adminType) throws Exception {
		List<TestConductor> testConductorList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;

		if (searchKey != null && active != null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_testConductorName", searchKey);
			parameters.put("_1_active", active);
			parameters.put("_3_adminType", UserType.valueOf(adminType));
			testConductorList = testConductorDao.listEntityByParameter(TestConductorDao.findAllBySearchKeyWithFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			parameters.put("_3_adminType", UserType.valueOf(adminType));
			testConductorList = testConductorDao.listEntityByParameter(TestConductorDao.findAllByFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_testConductorName", searchKey);
			parameters.put("_3_adminType", UserType.valueOf(adminType));
			testConductorList = testConductorDao.listEntityByParameter(TestConductorDao.findAllBySearchKey, parameters,
					startIndex, pageSize);
		} else {
			parameters.put("_3_adminType", UserType.valueOf(adminType));
			testConductorList = testConductorDao.listEntityByParameter(TestConductorDao.findAll, parameters, startIndex,
					pageSize);
		}
		Map<String, Object> testConductorResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			testConductorResultSet.put("count", testConductorList.size());
			List<TestConductorVo> testConductorVos = new ArrayList<>();
			testConductorList.stream().skip(startIndex).limit(pageSize).forEach(testConductor -> {
				testConductorVos.add(Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor));
			});
			testConductorResultSet.put("testConductorVoList", testConductorVos);
		} else {
			List<TestConductorVo> testConductorVos = new ArrayList<>();
			testConductorList.forEach(testConductor -> {
				testConductorVos.add(Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor));
			});
			testConductorResultSet.put("testConductorVoList", testConductorVos);
		}

		return testConductorResultSet;
	}

	@Override
	public Map<String, Object> listExaminerByAdminId(Integer pageNo, Integer pageSize, Boolean active, Integer userId,
			String adminType) {
		Map<String, Object> testConductorResultSet = new HashMap<>();
		Map<String, Object> paramsKeyAndValues = new HashMap<>();
		paramsKeyAndValues.put("_1_parentTcId", userId);
		List<TestConductor> testConductorList = new ArrayList<>();
		Integer startIndex = (pageNo * pageSize) - pageSize;
		if (pageSize != 1) {
			if (active == null) {
				testConductorList = testConductorDao.listEntityByParameter(
						TestConductorDao.findAllTestConductorByParentId, paramsKeyAndValues, startIndex, pageSize);
			} else {
				paramsKeyAndValues.put("_2_active", active);
				testConductorList = testConductorDao.listEntityByParameter(
						TestConductorDao.findAllTestConductorByParentIdWithFilter, paramsKeyAndValues, startIndex,
						pageSize);
			}
			List<TestConductorVo> testConductorVos = new ArrayList<>();
			testConductorList.forEach(testConductor -> {
				testConductorVos.add(Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor));
			});
			testConductorResultSet.put("testConductorVoList", testConductorVos);
		} else {
			startIndex = (pageNo * pageSize) - pageSize;
			if (active == null) {
				testConductorList = testConductorDao.listEntityByParameter(
						TestConductorDao.findAllTestConductorByParentId, paramsKeyAndValues, null, null);
			} else {
				paramsKeyAndValues.put("_2_active", active);
				testConductorList = testConductorDao.listEntityByParameter(
						TestConductorDao.findAllTestConductorByParentIdWithFilter, paramsKeyAndValues, startIndex,
						pageSize);
			}
			testConductorResultSet.put("count", testConductorList.size());
			List<TestConductorVo> testConductorVos = new ArrayList<>();
			testConductorList.stream().skip(startIndex).limit(pageSize).forEach(testConductor -> {
				testConductorVos.add(Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor));
			});
			testConductorResultSet.put("testConductorVoList", testConductorVos);
		}

		return testConductorResultSet;
	}

}
