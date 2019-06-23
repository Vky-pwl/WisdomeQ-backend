package com.icat.quest.user.service;

import java.util.Map;

public interface CandidateLoginDetailService {

	Map<String, Object> getCandidateLoginDetails(String token, Boolean flag);


}
