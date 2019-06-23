package com.icat.quest.common.utils;

import com.icat.quest.common.vo.UserType;

public class PrincipalParseService {
	
	public static PrincipalVo trimPrincipalToken(String principalToken) {
		PrincipalVo principalVo = new PrincipalVo();
		String[] principalTokens = principalToken.split(Constants.OPERATION_PRINCIPAL_TOKENIZER) ;
		principalVo.setUserId(Integer.parseInt(principalTokens[0]));
		principalVo.setUserType(UserType.valueOf(principalTokens[1]));
		principalVo.setUserLoginId(Integer.parseInt(principalTokens[2]));
		if(principalTokens.length==4) {
		principalVo.setCollegeId(Integer.parseInt(principalTokens[3]));
		}
		return principalVo;
	}

}
