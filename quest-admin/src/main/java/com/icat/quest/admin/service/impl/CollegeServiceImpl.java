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

import com.icat.quest.admin.service.CollegeService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.UniqueCodeGeneratorImpl;
import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.dao.CollegeDao;
import com.icat.quest.model.College;

@Service
public class CollegeServiceImpl implements CollegeService {

	@Autowired
	private CollegeDao collegeDao;

	@Override
	public Integer createCollege(CollegeVo collegeVo, Integer userId) throws Exception {
		College college = new College();
		if (collegeVo == null || collegeVo.getCollegeName() == null) {
			throw new Exception("collegeName should not be null");
		}
		college.setActive(true);
		college.setCreated(new Date());
		college.setCreatedBy(new Long(userId));
		college.setLastModified(new Date());
		college.setLastModifiedBy(new Long(userId));
		college.setAddressLine1(collegeVo.getAddressLine1());
		college.setAddressLine2(collegeVo.getAddressLine2());
		college.setCity(collegeVo.getCity());
		college.setState(collegeVo.getState());
		college.setPinCode(collegeVo.getPinCode());
		college.setCountry(collegeVo.getCountry());
		college.setCollegeName(collegeVo.getCollegeName());
		Integer id = collegeDao.create(college);
		if (id == null) {
			throw new Exception("Record does not insert by technical reason");
		}
		college.setCollegeCode(UniqueCodeGeneratorImpl.getCollegeCode(collegeVo.getCollegeName(), id + ""));
		collegeDao.update(college);
		return id;
	}

	@Override
	public Integer updateCollege(CollegeVo collegeVo, Integer userId) throws Exception {
		if (collegeVo.getCollegeId() == null)
			throw new Exception("collegeId should not be null");

		College college = collegeDao.read(collegeVo.getCollegeId());
		if (college == null)
			throw new Exception("Record not found");

		if (collegeVo.getCollegeName() != null && !collegeVo.getCollegeName().trim().isEmpty())
			college.setCollegeName(collegeVo.getCollegeName().trim());

		if (collegeVo.getAddressLine1() != null)
			college.setAddressLine1(collegeVo.getAddressLine1().trim());
		if (collegeVo.getAddressLine2() != null)
			college.setAddressLine2(collegeVo.getAddressLine2().trim());
		if (collegeVo.getCity() != null)
			college.setCity(collegeVo.getCity().trim());
		if (collegeVo.getState() != null)
			college.setState(collegeVo.getState().trim());
		if (collegeVo.getCountry() != null)
			college.setCountry(collegeVo.getCountry().trim());
		if (collegeVo.getPinCode() != null)
			college.setPinCode(collegeVo.getPinCode().trim());
		if (collegeVo.getActive() != null)
			college.setActive(collegeVo.getActive());
		college.setLastModified(new Date());
		college.setLastModifiedBy(new Long(userId));

		collegeDao.update(college);
		return collegeVo.getCollegeId();
	}

	@Override
	public CollegeVo readCollege(Integer collegeId, Integer userId) throws Exception {
		if (collegeId == null)
			throw new Exception("collegeId should not be null");

		College college = collegeDao.read(collegeId);
		if (college == null)
			throw new Exception("Record not found");

			return Transformer.COLLEGE_TRANSFORMER.transform(college);
	}

	@Override
	public Map<String, Object> listCollege(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<College> collegeList = new ArrayList<>();
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
			parameters.put("_2_collegeName", searchKey);
			parameters.put("_1_active", active);
			collegeList = collegeDao.listEntityByParameter(CollegeDao.findAllBySearchKeyWithFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			collegeList = collegeDao.listEntityByParameter(CollegeDao.findAllByFilter, parameters, startIndex,
					pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_collegeName", searchKey);
			collegeList = collegeDao.listEntityByParameter(CollegeDao.findAllBySearchKey, parameters, startIndex,
					pageSize);
		} else {
			collegeList = collegeDao.listEntityByParameter(CollegeDao.findAll, parameters, startIndex, pageSize);
		}
		Map<String, Object> collegeResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			collegeResultSet.put("count", collegeList.size());
			List<CollegeVo> collegeVos = new ArrayList<>();
			collegeList.stream().skip(startIndex).limit(pageSize).forEach(college -> {
				collegeVos.add(Transformer.COLLEGE_TRANSFORMER.transform(college));
			});
			collegeResultSet.put("collegeVoList", collegeVos);

		} else {
			List<CollegeVo> collegeVos = new ArrayList<>();
			collegeList.forEach(college -> {
				collegeVos.add(Transformer.COLLEGE_TRANSFORMER.transform(college));
			});
			collegeResultSet.put("collegeVoList", collegeVos);

		}

		return collegeResultSet;
	}

}