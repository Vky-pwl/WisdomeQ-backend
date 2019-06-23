package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.model.Candidate;

public class CandidateTransformer implements Transformer<Candidate, CandidateVo> {

	@Override
	public CandidateVo transform(Candidate candidate) {
		if (candidate == null) {
			return null;
		}
		CandidateVo candidateVo = new CandidateVo();
		candidateVo.setUserId(candidate.getUserId());
		candidateVo.setAddressLine1(candidate.getAddressLine1());
		candidateVo.setAddressLine2(candidate.getAddressLine2());
		candidateVo.setCity(candidate.getCity());
		candidateVo.setState(candidate.getState());
		candidateVo.setCountry(candidate.getCountry());
		candidateVo.setPinCode(candidate.getPinCode());
		candidateVo.setActive(candidate.getActive());
		candidateVo.setFirstName(candidate.getFirstName());
		candidateVo.setMiddleName(candidate.getMiddleName());
		candidateVo.setLastName(candidate.getLastName());
		candidateVo.setGender(candidate.getGender().name());
		candidateVo.setDateOfBirth(candidate.getDateOfBirth() != null ? candidate.getDateOfBirth().getTime() : null);
		candidateVo.setAdhaarNumber(candidate.getAdhaarNumber());
		candidateVo.setTenthPercentage(candidate.getTenthPercentage());
		candidateVo.setContactEmail(candidate.getContactEmail());
		candidateVo.setContactNumber(candidate.getContactNumber());
		candidateVo.setCollegeVo(Transformer.COLLEGE_TRANSFORMER.transform(candidate.getCollege()));
		candidateVo
				.setSpecializationVo(Transformer.SPECIALIZATION_TRANSFORMER.transform(candidate.getSpecialization()));
		return candidateVo;
	}

}
