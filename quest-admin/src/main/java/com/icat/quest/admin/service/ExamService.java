package com.icat.quest.admin.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.common.vo.UserType;

public interface ExamService {

	Integer createExam(ExamVo examVo,Integer userId)throws Exception;

	Integer updateExam(ExamVo examVo,Integer userId) throws Exception;

	ExamVo readExam(Integer examId,Integer userId) throws Exception;

	Map<String,Object> listExam(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId,UserType userType, Boolean publish) throws Exception;

	Map<String, Object> listExamByExamIdList(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, List<Integer> examIdList, UserType userType, Boolean publish) throws Exception;

	void publishUpdate(Integer examId, Integer userId);

}
