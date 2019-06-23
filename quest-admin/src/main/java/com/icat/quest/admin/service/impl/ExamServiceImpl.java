/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.ExamService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.UniqueCodeGeneratorImpl;
import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamCategoryDao;
import com.icat.quest.dao.ExamDao;
import com.icat.quest.model.Exam;
import com.icat.quest.model.ExamHasSettings;
import com.icat.quest.service.ExamHasSettingsService;
import com.icat.quest.service.UserHasPermissionService;

@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examDao;
	@Autowired
	private ExamCategoryDao examCategoryDao;
	@Autowired
	private UserHasPermissionService userHasPermissionService;
	@Autowired
	private ExamHasSettingsService		examHasSettingsService;
	private static Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

	@Override
	public Integer createExam(ExamVo examVo, Integer userId) throws Exception {

		inputValidation(examVo);
		Exam exam = new Exam();
		exam.setCreated(new Date());
		exam.setCreatedBy(new Long(userId));
		exam.setLastModified(new Date());
		exam.setLastModifiedBy(new Long(userId));
		exam.setActive(true);
		if (examVo.getStartDate() != null) {
			exam.setStartDate(new Date(examVo.getEndDate()));
		}
		if (examVo.getEndDate() != null) {
			exam.setEndDate(new Date(examVo.getEndDate()));
		}

		exam.setExamDescription(
				examVo.getExamDescription() != null ? examVo.getExamDescription().trim().getBytes() : new byte[0]);
		exam.setExamInstructions(
				examVo.getExamInstructions() != null ? examVo.getExamInstructions().trim().getBytes() : new byte[0]);
		exam.setExamName(examVo.getExamName());
		exam.setSectionCount(examVo.getSectionCount());
		exam.setAllowReattempts(examVo.getAllowReattempts());
		exam.setDurationInSeconds(examVo.getDurationInSeconds());
		exam.setNegativeMarking(examVo.getNegativeMarking());
		exam.setPassingPercentage(examVo.getPassingPercentage());
		exam.setQuestionCount(examVo.getQuestionCount());
		exam.setStatus(examVo.getStatus());
		exam.setTotalMarks(examVo.getTotalMarks());
		exam.setPublish(false);
		if (examVo.getExamCategoryVo().getExamSubCategoryId() != null) {
			exam.setExamCategory(examCategoryDao.read(examVo.getExamCategoryVo().getExamSubCategoryId()));
		} else {
			exam.setExamCategory(examCategoryDao.read(examVo.getExamCategoryVo().getExamCategoryId()));
		}
		if(examVo.getExamSettingsVo() != null) {
		ExamHasSettings examHasSettings =  examHasSettingsService.createSetting(examVo.getExamSettingsVo(), userId);
		exam.setExamHasSettings(examHasSettings);
		}
		Integer id = examDao.create(exam);
		
		exam.setTestCode(UniqueCodeGeneratorImpl.getExamCode(exam.getExamName(), exam.getExamId() + ""));
		examDao.update(exam);
		return id;
	}

	@Override
	public Integer updateExam(ExamVo examVo, Integer userId) throws Exception {
		if (examVo.getExamId() == null) {
			logger.warn("ExamId is missing");
			throw new Exception("examId should not be null");
		}

		Exam exam = examDao.read(examVo.getExamId());
		if (exam == null) {
			logger.warn("Record not found on examId: " + examVo.getExamId());
			throw new Exception("Record not found");
		}

		inputValidation(examVo);

		if (examVo.getExamName() != null) {
			exam.setExamName(examVo.getExamName().trim());
		}

		if (examVo.getStartDate() != null) {
			exam.setStartDate(new Date(examVo.getStartDate()));
		}
		if (examVo.getEndDate() != null) {
			exam.setEndDate(new Date(examVo.getEndDate()));
		}

		if (examVo.getExamDescription() != null) {
			exam.setExamDescription(examVo.getExamDescription().trim().getBytes());
		}
		if (examVo.getExamInstructions() != null) {
			exam.setExamInstructions(examVo.getExamInstructions().trim().getBytes());
		}
		if (examVo.getExamName() != null) {
			exam.setExamName(examVo.getExamName().trim());
		}
		if (examVo.getSectionCount() != null) {
			exam.setSectionCount(examVo.getSectionCount());
		}
		if (examVo.getActive() != null) {
			exam.setActive(examVo.getActive());
		}
		if (examVo.getAllowReattempts() != null) {
			exam.setAllowReattempts(examVo.getAllowReattempts());
		}
		if (examVo.getDurationInSeconds() != null) {
			exam.setDurationInSeconds(examVo.getDurationInSeconds());
		}
		if (examVo.getNegativeMarking() != null) {
			exam.setNegativeMarking(examVo.getNegativeMarking());
		}
		if (examVo.getPassingPercentage() != null) {
			exam.setPassingPercentage(examVo.getPassingPercentage());
		}
		if (examVo.getQuestionCount() != null) {
			exam.setQuestionCount(examVo.getQuestionCount());
		}
		if (examVo.getStatus() != null) {
			exam.setStatus(examVo.getStatus());
		}
		if (examVo.getTotalMarks() != null) {
			exam.setTotalMarks(examVo.getTotalMarks());
		}
		if (examVo.getExamCategoryVo() != null && examVo.getExamCategoryVo().getExamSubCategoryId() != null) {
			exam.setExamCategory(examCategoryDao.read(examVo.getExamCategoryVo().getExamSubCategoryId()));
		} else if (examVo.getExamCategoryVo() != null && examVo.getExamCategoryVo().getExamCategoryId() != null) {
			exam.setExamCategory(examCategoryDao.read(examVo.getExamCategoryVo().getExamCategoryId()));
		}
		exam.setLastModified(new Date());
		exam.setLastModifiedBy(new Long(userId));

		examDao.update(exam);
		return examVo.getExamId();
	}
	@Override
	public void publishUpdate(Integer examId, Integer userId) {
		Exam exam = examDao.read(examId);
		exam.setPublish(true);
		exam.setLastModified(new Date());
		exam.setLastModifiedBy(new Long(userId));
		examDao.update(exam);
	}

	@Override
	public ExamVo readExam(Integer examId, Integer userId) throws Exception {
		if (examId == null) {
			throw new Exception("examId should not be null");
		}
		Exam exam = examDao.read(examId);
		if (exam == null) {
			throw new Exception("Record not found");
		}
		return Transformer.EXAM_TRANSFORMER.transform(exam);
	}

	@Override
	public Map<String, Object> listExam(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, UserType userType, Boolean publish) throws Exception {

		List<Exam> examList = new ArrayList<>();
		if (pageNo == null) {
			pageNo = 50;
		}
		if (pageSize == null) {
			pageSize = 1;
		}
		Integer startIndex = null;
		if (pageNo != 1) {
			startIndex = (pageNo * pageSize) - pageSize;
		}
		List<Boolean> publishes = new ArrayList<>();
		List<Boolean> actives = new ArrayList<>();
		if(publish == null) {
			publishes.add(true); publishes.add(false);
		} else {
			publishes.add(publish);
		}
		if(active == null) {
			actives.add(true);actives.add(false);
		} else {
			actives.add(active);
		}
		if(searchKey == null) {
			searchKey = "%" +""+ "%";
		} else	{
			searchKey = "%"+searchKey + "%";		
		}
		Map<String, Object> examResultSet = new HashMap<>();
		List<ExamVo> examVos = new ArrayList<>();
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("_1_active", actives);
		parameters.put("_3_publish", publishes);
		parameters.put("_2_examName", searchKey);
		
		if (pageSize != 1) {
			examList = examDao.listEntityByParameter(ExamDao.findAllBySearchKeyWithFilter, parameters, startIndex,
					pageSize);
			examList.forEach(exam -> {
				examVos.add(Transformer.EXAM_TRANSFORMER.transform(exam));
			});
			examResultSet.put("examVoList", examVos);
		}  else {
			examList = examDao.listEntityByParameter(ExamDao.findAllBySearchKeyWithFilter, parameters, null, null);
			examResultSet.put("count", examList.size());
			examList.stream().skip(startIndex).limit(pageSize).forEach(exam -> {
				examVos.add(Transformer.EXAM_TRANSFORMER.transform(exam));
			});
			examResultSet.put("examVoList", examVos);
		}

		if (userType != null && userId != null) {
			Map<Integer, List<PermissionVo>> permissionMap = userHasPermissionService
					.getPermissionListGroupByExamId(userId, userType);
			for (ExamVo examVo : examVos) {
				if (permissionMap.containsKey(examVo.getExamId())) {
					examVo.setPermissionVoList(permissionMap.get(examVo.getExamId()));
				}
			}
		}
		return examResultSet;
	}

	@Override
	public Map<String, Object> listExamByExamIdList(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, List<Integer> examIdList, UserType userType, Boolean publish) throws Exception {

		List<Exam> examList = new ArrayList<>();
		if (pageNo == null) {
			pageNo = 50;
		}
		if (pageSize == null) {
			pageSize = 1;
		}
		Integer startIndex = null;
		if (pageNo != 1) {
			startIndex = (pageNo * pageSize) - pageSize;
		}
		List<Boolean> publishes = new ArrayList<>();
		List<Boolean> actives = new ArrayList<>();
		if(publish == null) {
			publishes.add(true); publishes.add(false);
		} else {
			publishes.add(publish);
		}
		if(active == null) {
			actives.add(true);actives.add(false);
		} else {
			actives.add(active);
		}
		if(searchKey == null) {
			searchKey = "%" +""+ "%";
		} else	{
			searchKey = "%"+searchKey + "%";		
		}
		Map<String, Object> examResultSet = new HashMap<>();
		List<ExamVo> examVos = new ArrayList<>();
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("_1_active", actives);
		parameters.put("_3_publish", publishes);
		parameters.put("_2_examName", searchKey);
		parameters.put("_4_examIdList", examIdList);
		
		if (pageSize != 1) {
			examList = examDao.listEntityByParameter(ExamDao.findAllBySearchKeyWithFilterAndExamIdList, parameters, startIndex,
					pageSize);
			examList.forEach(exam -> {
				examVos.add(Transformer.EXAM_TRANSFORMER.transform(exam));
			});
			examResultSet.put("examVoList", examVos);
		}  else {
			examList = examDao.listEntityByParameter(ExamDao.findAllBySearchKeyWithFilterAndExamIdList, parameters, null, null);
			examResultSet.put("count", examList.size());
			examList.stream().skip(startIndex).limit(pageSize).forEach(exam -> {
				examVos.add(Transformer.EXAM_TRANSFORMER.transform(exam));
			});
			examResultSet.put("examVoList", examVos);
		}

		if (userType != null && userId != null) {
			Map<Integer, List<PermissionVo>> permissionMap = userHasPermissionService
					.getPermissionListGroupByExamId(userId, userType);
			for (ExamVo examVo : examVos) {
				if (permissionMap.containsKey(examVo.getExamId())) {
					examVo.setPermissionVoList(permissionMap.get(examVo.getExamId()));
				}
			}
		}
		return examResultSet;
	}

	private void inputValidation(ExamVo examVo) throws Exception {
		if (examVo.getDurationInSeconds() == null) {
			logger.warn("DurationInSeconds is missing");
			throw new Exception("DurationInSeconds should not be null");
		}
		if (examVo.getTotalMarks() == null) {
			logger.warn("TotalMarks is missing");
			throw new Exception("TotalMarks should not be null");
		}
		if (examVo.getQuestionCount() == null) {
			logger.warn("QuestionCount is missing");
			throw new Exception("TotalQuestion should not be null");
		}
		if (examVo.getSectionCount() == null) {
			logger.warn("SectionCount is missing");
			throw new Exception("SectionCount should not be null");
		}

		if (examVo.getExamCategoryVo() == null && (examVo.getExamCategoryVo().getExamCategoryId() == null
				|| examVo.getExamCategoryVo().getExamSubCategoryId() == null)) {
			logger.warn("TotalMarks is missing");
			throw new Exception("ExamCategory should not be null");
		}
	}

}
