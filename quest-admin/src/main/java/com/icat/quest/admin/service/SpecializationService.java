package com.icat.quest.admin.service;

import java.util.Map;

import com.icat.quest.common.vo.SpecializationVo;

public interface SpecializationService {

	Integer createSpecialization(SpecializationVo collageVo,Integer userId);

	Integer updateSpecialization(SpecializationVo collageVo,Integer userId) throws Exception;

	
	SpecializationVo readSpecialization(Integer specializationId,Integer userId) throws Exception;

	Map<String,Object> listSpecialization(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId)
			throws Exception;

}
