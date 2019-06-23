package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.SpecializationVo;
import com.icat.quest.model.Specialization;

public class SpecializationTransformer implements Transformer<Specialization, SpecializationVo> {

	@Override
	public SpecializationVo transform(Specialization specialization) {
		if (specialization == null) {
			return null;
		}
		SpecializationVo specializationVo = new SpecializationVo();
		specializationVo.setSpecializationId(specialization.getSpecializationId());
		specializationVo.setSpecializationName(specialization.getSpecializationName());
		specializationVo.setSpecializationCode(specialization.getSpecializationCode());
		specializationVo.setActive(specialization.getActive());
		return specializationVo;
	}

}
