/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.UserHasPermission;


public interface UserHasPermissionDao extends GenericDao<UserHasPermission,Integer>{


	public String findAllPermissionByUserIdAndUserType="from com.icat.quest.model.UserHasPermission userHasPermission where "
			+ "userHasPermission.userId =:_1_userId and userHasPermission.userType =:_2_userType and" +
			" userHasPermission.active = :_3_active order by userHasPermission.userHasPermissionId desc";
	
	public String findAllPermissionByUserIdAndUserTypeExamId="from com.icat.quest.model.UserHasPermission userHasPermission where "
			+ "userHasPermission.userId =:_1_userId and userHasPermission.userType =:_2_userType and " +
			"userHasPermission.active = :_3_active and userHasPermission.exam.examId =:_4_examId order by " +
			"userHasPermission.userHasPermissionId desc";
	
	public String findAllByExamIdAndUserId="from com.icat.quest.model.UserHasPermission userHasPermission where "
			+ "userHasPermission.exam.examId =:_1_examId and userHasPermission.userId =:_3_userId " +
			"and userHasPermission.userType=:_4_userType";

	public String checkEligble = "select userId from UserHasPermission where examId =:_1_examId and " +
			"userId =:_2_userId and userType=:_3_userType and permissionId = :_4_permissionId and active = true";

	public String checkEligbleTemp = "select UHP.permissionId from UserHasPermission UHP, TestConductor TC " +
			"where TC.TestconductorId=UHP.userId and TC.collegeId=:_1_collegeId and UHP.active =true " +
			"and UHP.permissionId=:_2_permissionId and UHP.examId=:_3_examId";

	public String findAllPermissions="from com.icat.quest.model.UserHasPermission userHasPermission where " +
	"userHasPermission.active = :_1_active order by userHasPermission.userHasPermissionId desc";
}
