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

import com.icat.quest.admin.service.SpecializationService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.SpecializationVo;
import com.icat.quest.dao.SpecializationDao;
import com.icat.quest.model.Specialization;

@Service
public class SpecializationServiceImpl implements SpecializationService {

	@Autowired
	private SpecializationDao specializationDao;

	@Override
	public Integer createSpecialization(SpecializationVo specializationVo, Integer userId) {

		Specialization specialization = new Specialization(specializationVo.getSpecializationName(),
				specializationVo.getSpecializationCode(), true, new Date(), new Long(userId), new Date(),
				new Long(userId));
		Integer id = specializationDao.create(specialization);
		return id;
	}

	@Override
	public Integer updateSpecialization(SpecializationVo specializationVo, Integer userId) throws Exception {
		if (specializationVo.getSpecializationId() == null)
			throw new Exception("SpecializationId should not be null");

		Specialization specialization = specializationDao.read(specializationVo.getSpecializationId());
		if (specialization == null)
			throw new Exception("Record not found");

		if (specializationVo.getSpecializationName() != null) {
			specialization.setSpecializationName(specializationVo.getSpecializationName().trim());
		}
		if (specializationVo.getActive() != null) {
			specialization.setActive(specializationVo.getActive());
		}
		specialization.setLastModified(new Date());
		specialization.setLastModifiedBy(new Long(userId));
		specializationDao.update(specialization);
		return specializationVo.getSpecializationId();
	}

	@Override
	public SpecializationVo readSpecialization(Integer specializationId, Integer userId) throws Exception {
		if (specializationId == null) {
			throw new Exception("SpecializationId should not be null");
		}
		Specialization specialization = specializationDao.read(specializationId);
		if (specialization == null) {
			throw new Exception("Record not found");
		}
		return Transformer.SPECIALIZATION_TRANSFORMER.transform(specialization);
	}

	@Override
	public Map<String, Object> listSpecialization(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {
		List<Specialization> specializationList = new ArrayList<>();
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
			parameters.put("_2_specializationName", searchKey);
			parameters.put("_1_active", active);
			specializationList = specializationDao.listEntityByParameter(SpecializationDao.findAllBySearchKeyWithFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			specializationList = specializationDao.listEntityByParameter(SpecializationDao.findAllByFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_specializationName", searchKey);
			specializationList = specializationDao.listEntityByParameter(SpecializationDao.findAllBySearchKey,
					parameters, startIndex, pageSize);
		} else {
			specializationList = specializationDao.listEntityByParameter(SpecializationDao.findAll, parameters,
					startIndex, pageSize);
		}
		Map<String, Object> specializationResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			specializationResultSet.put("count", specializationList.size());
			List<SpecializationVo> specializationVos = new ArrayList<>();
			specializationList.stream().skip(startIndex).limit(pageSize).forEach(specialization -> {
				specializationVos.add(Transformer.SPECIALIZATION_TRANSFORMER.transform(specialization));
			});
			specializationResultSet.put("specializationVoList", specializationVos);
		} else {
			List<SpecializationVo> specializationVos = new ArrayList<>();
			specializationList.forEach(specialization -> {
				specializationVos.add(Transformer.SPECIALIZATION_TRANSFORMER.transform(specialization));
			});
			specializationResultSet.put("specializationVoList", specializationVos);
		}

		return specializationResultSet;
	}

}
