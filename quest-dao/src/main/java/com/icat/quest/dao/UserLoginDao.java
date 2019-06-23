

package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.UserLogin;

public interface UserLoginDao extends GenericDao<UserLogin, Integer> {

	
	
		public static String findAllLogOffTimeNullByUserName= "from com.icat.quest.model.UserLogin userLogin\n" + 
			"where userLogin.userId = :_1_userId and userLogin.userType=:_2_userType and userLogin.logoffTime is null order by id desc";

	
}
