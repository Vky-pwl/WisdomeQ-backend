
package com.icat.quest.common.utils;

public class Constants {
	
	public static final String	LINK_ATTR_USER		= "USER";
	public static final String	LINK_ATTR_EXAM			= "EXAM";
	
	public static final String	CLAIM_ATTR_ISS			= "iss";
	public static final String	CLAIM_ATTR_ISS_VAL		= "quest-security";
	public static final String	CLAIM_ATTR_SUB			= "sub";
	public static final String	CLAIM_ATTR_EXP			= "exp";
	public static final String	CLAIM_ATTR_IAT			= "iat";
	public static final String	CLAIM_ATTR_JTI			= "jti";
	public static final String	CLAIM_ATTR_TYPE			= "type";
	public static final String	CLAIM_ATTR_ULID			= "ulId";
	public static final String	CLAIM_ATTR_CLID			= "clId";
	
	public static final String EXCEPTION_MESSAGE_SEPERATOR = "~";
	public static final String EXCEPTION_LEVEL_DEBUG = "debug";

	public static final String CACHE_KEY_APPLICATION_CLASSES_LIST = "application-classes-list";
	public static final String CACHE_KEY_APPLICATION_EXCEPTION_CLASSES_LIST = "application-exception-classes-list";

	public static final String SESSION_APP_LOCALE = "APP_LOCALE";
	public static final String REQUEST_ATTR_USERNAME = "userName";
	public static final String REQUEST_APP_LOCALE = "APP_LOCALE";
	public static final String BEARER_AUTH_PASSWORD = "Bearer";
	public static final String DISPATCHER_END_POINT = "/quest-receiver";
	public static final String	REQUEST_USERNAME		= "userName";
	public static final String	REQUEST_PASSWORD		= "password";
	public static final String	REQUEST_USER_TYPE		= "userType";
	public static final String	REQUEST_REFRESH_TOKEN	= "refreshToken";

	
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_ERROR = "error";
	
	
	public static final String URL_API_VERSION = "/v1.0";
	public static final String URL_API_BASE = "/api" + URL_API_VERSION;
	public static final String URL_API_UI_BASE = URL_API_BASE + "/ui";

	
	public static final String RESPONSE_ID = "id";
	public static final String RESPONSE_OBJECT = "object";
	public static final String RESPONSE_LIST = "list";

	public static final String RESPONSE_STATUS = "status";
	public static final String RESPONSE_STATUS_SUCCESS = "success";
	public static final String RESPONSE_STATUS_ERROR = "error";

	public static final String RESPONSE_MESSAGE = "message";
	public static final String RESPONSE_EXCEPTION = "exception";

	public static final String RESPONSE_ID_LIST = "idList";

	public static final String RESPONSE_PAGE_NO = "pageNo";
	public static final String RESPONSE_PAGE_SIZE = "pageSize";
	public static final String RESPONSE_LIST_SIZE = "listSize";
	public static final String RESPONSE_PAGE_TOTAL = "pageTotal";
	public static final String RESPONSE_PAGING_ENABLED = "pagingEnabled";
	public static final String OPERATION_CREATE = "/create";
	public static final String OPERATION_ATTACH = "/attach";
	public static final String OPERATION_UPDATE = "/update";
	public static final String OPERATION_PUBLISH = "/publish";
	public static final String OPERATION_DEACTIVATE = "/deactivate";
	public static final String OPERATION_READ = "/read";
	public static final String OPERATION_LIST = "/list";
	public static final String OPERATION_USER_EXAM_SUMMARY =URL_API_UI_BASE+ "/user-exam-summary";
	
	public static final String OPERATION_COLLEGE =URL_API_UI_BASE+ "/college";
	public static final String OPERATION_USER = URL_API_UI_BASE+"/user";
	public static final String OPERATION_SPECIALZATION = URL_API_UI_BASE+"/specialization";
	public static final String OPERATION_LOGIN = "/login";
	public static final String OPERATION_LOGOUT = "/logout";
	public static final String OPERATION_TEST_CONDUCTOR = URL_API_UI_BASE+"/test-conductor";
	public static final String OPERATION_TEST_CONDUCTOR_LOGIN = URL_API_BASE+"/test-conductor";
	public static final String OPERATION_CANDIDATE_LOGIN = URL_API_BASE+"/candidate";
	
	public static final String OPERATION_PERMISSION =URL_API_UI_BASE+ "/permission";
	public static final String OPERATION_EXAM_SETTING =URL_API_UI_BASE+ "/exam-setting";
	public static final String OPERATION_MASTER_SETTING =URL_API_UI_BASE+ "/master-setting";
	public static final String OPERATION_USER_HAS_PERMISSION =URL_API_UI_BASE+ "/user-permission";
	public static final String OPERATION_TEST_CONDUCTOR_LICENCE = URL_API_UI_BASE+"/test-conductor-license";
	public static final String OPERATION_EXAM_LICENCE = URL_API_UI_BASE+"/exam-license";
	public static final String OPERATION_TEST_CONDUCTOR_HAS_TEST_CODE = URL_API_UI_BASE+"/test-conductor-test-code";
	
	public static final String OPERATION_CREATE_BATCH = "/create-batch";
	
	public static final String OPERATION_REGISTER = "/register";
	public static final String OPERATION_SIGNUP = "/signup";
	
	public static final String OPERATION_EXAM =URL_API_UI_BASE+ "/exam";
	public static final String OPERATION_EXAM_SECTION =URL_API_UI_BASE+ "/exam-section";
	public static final String OPERATION_QUESTIONBANK =URL_API_UI_BASE+ "/question-bank";
	public static final String OPERATION_EXAM_SECTION_HAS_QUESTION =URL_API_UI_BASE+ "/exam-section-question";
	public static final String OPERATION_EXPLANATION_DESCRIPTION =URL_API_UI_BASE+ "/explanation-description";
	public static final String OPERATION_LIST_BY_EXAMID = "/list-exam-id";
	public static final String OPERATION_LIST_BY_EXAMSECTIONID = "/list-examSection-id";
	public static final String OPERATION_READ_BY_QUESTIONID = "/read-by-question-id";
	public static final String OPERATION_EXAM_CATEGORY =URL_API_UI_BASE+ "/exam-category";
	public static final String OPERATION_QUESTION_CATEGORY =URL_API_UI_BASE+ "/question-category";
	public static final String OPERATION_LIST_BY_CATEGORY_ID ="/list-by-category-id";
	public static final String OPERATION_SUB_CATEGORY_LIST ="/sub-category-list";
	public static final String OPERATION_LIST_BY_TEST_CONDUCTOR_ID ="/list-by-tc-id";
	public static final String OPERATION_USER_ASSIGNED_TEST_CODE ="/test-code-assign-user";
	public static final String OPERATION_LIST_TEST_CONDUCTOR_LICENCE_ID ="/list-test-conductor-license-id";
	public static final String OPERATION_USER_DEACTIVATE_TEST_CODE ="/test-code-deactivate-user";
	public static final String OPERATION_TC_ASSIGNED_LICENSE ="/tc-assign-license";
	public static final String OPERATION_ADMIN_ASSIGNED_LICENSE ="/admin-assign-license";
	public static final String OPERATION_TESTCONDUCTOR_LIST = "/tc-list";
	public static final String OPERATION_SUB_SECTION_LIST = "/list-sub-section";
	public static final String OPERATION_EXTERNAL_LICENSE_ASSIGN ="/external-license-assign";
	public static final String OPERATION_EXTERNAL_LICENSE_LIST ="/external-license-list";
	public static final String OPERATION_LIST_BY_EXAM_ID ="/list-by-exam-id";
	public static String OPERATION_PRINCIPAL_TOKENIZER = "_";
	
	public static final String OPERATION_CANDIDATE_EXAM =URL_API_UI_BASE+ "/candidate-exam";
	public static final String OPERATION_RESULT_LIST ="/result-list";
	
	public static final String CACHE_EXAM_SECTION_KEY = "EXAM_SECTION";
	
	public static final String OPERATION_TINYLINK_LOGIN = "/tinylink-login";
	public static final String OPERATION_TINYLINK_RESUME = "/tinylink-resume";
	public static final String OPERATION_TINYLINK_EXAM_STATUS = "/tinylink-status";
	
	public static final String OPERATION_EXAM_LIST = URL_API_BASE+"/exam-list";
	
	public static final String OPERATION_START = "/start";
	public static final String OPERATION_SUBMIT = "/submit";
	public static final String OPERATION_STATUS_CHANGE = "/status-change";
	
	public static final String OPERATION_METADATA = "/metadata";
	public static final String OPERATION_EXAM_RESULT_LIST = "/result-list";
	
	public static final String OPERATION_EXAM_LIST_BY_EXAM_ID = "/result-list-by-examId";
	public static final String OPERATION_RESULT = "/result";
	
	public static final String OPERATION_GET_QUESTION = "/get-question";

	public static final String OPERATION_GET_QUESTION_BY_QUESTION_ID = "/get-question-question-id";
	public static final String OPERATION_CAND_RESULT_BY_EXAMID = "/result-by-examId";
	
	public static final String OPERATION_CERTIFICATE ="/get-certificate";
	public static final String OPERATION_EAI_CERTIFICATE ="/get-eai-certificate";
	public static final String OPERATION_EAI_SUMMARY ="/get-eai-summary";
	
	public static final String OPERATION_EXAM_LIST_TOTAL_ATTEMPT = "/result-total-attempt";
	public static final String OPERATION_EXAM_LIST_RANK = "/result-rank";
	public static final String OPERATION_GET_QUESTION_LIST = "/get-question-list";
	public static final String OPERATION_SUBMIT_ANSWER = "/submit-answer";
	public static final String OPERATION_QUESTION_STATUS_UPDATE = "/question-status-update";	
	
	public static final Integer PERMISSION_VIEW_EAI_CERTIFICATE = 7;
	public static final Integer PERMISSION_VIEW_CERTIFICATE = 1;
	public static final Integer PERMISSION_DOWNLOAD_CERTIFICATE = 2;
	public static final Integer PERMISSION_VIEW_EXPLANATION = 3;
	//public static final Integer PERMISSION_VIEW_EAI_CERTIFICATE = 4;
	//public static final Integer PERMISSION_VIEW_EAI_CERTIFICATE = 5;
	//public static final Integer PERMISSION_VIEW_EAI_CERTIFICATE = 6;
	
	
}
