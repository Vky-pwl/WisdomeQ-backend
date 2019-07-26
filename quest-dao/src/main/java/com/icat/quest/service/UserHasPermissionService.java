/**
 * 
 */
package com.icat.quest.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.common.vo.UserHasPermissionBatchVo;
import com.icat.quest.common.vo.UserHasPermissionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.model.Exam;

public interface UserHasPermissionService {


	void updateBatchUserHasPermission(List<Integer> userHasPermissionIdList, Integer userId) throws Exception;

	UserHasPermissionVo readUserHasPermission(Integer userHasPermissionId, Integer userId) throws Exception;

	List<Integer> craeteBatchUserHasPermission(UserHasPermissionBatchVo userHasPermissionBatchVo, Integer userId)
			throws Exception;

	List<UserHasPermissionVo> listUserHasPermission(Integer userId, UserType userType, Boolean active, Integer examId) throws Exception;

	Map<Integer, List<PermissionVo>> getPermissionListGroupByExamId(Integer userId, UserType userType,
																	List<Exam> examList);

	boolean isPermissionTemp(Integer collegeId, Integer examId, Integer permissionId);

	boolean isPermission(Integer userId, Integer examId, UserType userType, Integer permissionId);

	
		
}
