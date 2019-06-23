package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.CollegeVo;
import com.icat.quest.model.College;

public class CollegeTransformer implements Transformer<College, CollegeVo> {

	@Override
	public CollegeVo transform(College college) {
		if (college == null) {
			return null;
		}
		CollegeVo collegeVo = new CollegeVo();
		collegeVo.setCollegeId(college.getCollegeId());
		collegeVo.setCollegeName(college.getCollegeName());
		collegeVo.setCollegeCode(college.getCollegeCode());
		collegeVo.setAddressLine1(college.getAddressLine1());
		collegeVo.setAddressLine2(college.getAddressLine2());
		collegeVo.setCity(college.getCity());
		collegeVo.setState(college.getState());
		collegeVo.setCountry(college.getCountry());
		collegeVo.setPinCode(college.getPinCode());
		collegeVo.setActive(college.getActive());
		return collegeVo;
	}
}
