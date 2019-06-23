package com.icat.quest.app.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.icat.quest.auth.filter.AuthAuthenticatingFilter;
import com.icat.quest.auth.filter.CustomLogoutFilter;
import com.icat.quest.auth.relam.AuthAuthRealm;
import com.icat.quest.auth.service.SecurityServiceImpl;
import com.icat.quest.common.utils.SystemConfigResolver;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.CandidateExamDao;
import com.icat.quest.dao.CandidateExamSummaryDao;
import com.icat.quest.dao.CandidateQuestionStatusDao;
import com.icat.quest.dao.CollegeDao;
import com.icat.quest.dao.ExamCategoryDao;
import com.icat.quest.dao.ExamDao;
import com.icat.quest.dao.ExamHasSettingsDao;
import com.icat.quest.dao.ExamSectionDao;
import com.icat.quest.dao.ExamSectionHasQuestionDao;
import com.icat.quest.dao.ExamSettingDao;
import com.icat.quest.dao.ExamSettingHasCandidateDao;
import com.icat.quest.dao.ExplanationDescriptionDao;
import com.icat.quest.dao.MasterSettingDao;
import com.icat.quest.dao.PermissionDao;
import com.icat.quest.dao.PublishExamLicenseDao;
import com.icat.quest.dao.QuestionBankDao;
import com.icat.quest.dao.QuestionCategoryDao;
import com.icat.quest.dao.QuestionDescriptionDao;
import com.icat.quest.dao.QuestionOptionDao;
import com.icat.quest.dao.SicoSectionMarksDao;
import com.icat.quest.dao.SpecializationDao;
import com.icat.quest.dao.TestConductorDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.TestConductorLicenseDao;
import com.icat.quest.dao.UserHasPermissionDao;
import com.icat.quest.dao.UserLoginDao;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;
import com.icat.quest.dao.user.service.impl.ExamQuestionCacheServiceImpl;
import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.generic.dao.framework.impl.GenericDaoHibernateImpl;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.CandidateExam;
import com.icat.quest.model.CandidateExamSummary;
import com.icat.quest.model.CandidateQuestionStatus;
import com.icat.quest.model.College;
import com.icat.quest.model.Exam;
import com.icat.quest.model.ExamCategory;
import com.icat.quest.model.ExamHasSettings;
import com.icat.quest.model.ExamSection;
import com.icat.quest.model.ExamSectionHasQuestion;
import com.icat.quest.model.ExamSetting;
import com.icat.quest.model.ExamSettingHasCandidate;
import com.icat.quest.model.ExplanationDescription;
import com.icat.quest.model.MasterSetting;
import com.icat.quest.model.Permission;
import com.icat.quest.model.PublishExamLicense;
import com.icat.quest.model.QuestionBank;
import com.icat.quest.model.QuestionCategory;
import com.icat.quest.model.QuestionDescription;
import com.icat.quest.model.QuestionOption;
import com.icat.quest.model.SicoSectionMarks;
import com.icat.quest.model.Specialization;
import com.icat.quest.model.TestConductor;
import com.icat.quest.model.TestConductorHasTestCode;
import com.icat.quest.model.TestConductorLicense;
import com.icat.quest.model.UserHasPermission;
import com.icat.quest.model.UserLogin;
import com.icat.quest.utils.TinyLinkService;

@Configuration
@EnableTransactionManagement
public class DbHibernateConfig<T, PK extends Serializable> {

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		Map<String, String> map = new HashMap<String, String>();
		map.put("/admin/**", "authc, roles[admin]");
		map.put("/docs/**", "questAuthc, perms[document:read]");
		map.put("/api/**/ui/**", "questAuthc");
		map.put("/quest-receiver/**", "questAuthc");
		map.put("/api/**/logout", "logout");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

		return shiroFilterFactoryBean;
	}

	@Bean("logout")
	public CustomLogoutFilter logout() {
		CustomLogoutFilter customLogoutFilter = new CustomLogoutFilter();
		customLogoutFilter.setRedirectUrl("/login");
		return customLogoutFilter;
	}

	@Bean("questAuthc")
	public AuthAuthenticatingFilter vcaAuthc() {

		return new AuthAuthenticatingFilter();
	}

	@Bean("examQuestionCacheService")
	public ExamQuestionCacheService examQuestionCacheService() {
		return new ExamQuestionCacheServiceImpl();
	}
	
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(authAuthRealm());

		return defaultWebSecurityManager;
	}

	@Bean
	public DelegatingFilterProxy lifecycleBeanPostProcessor() {
		return new DelegatingFilterProxy();
	}

	@Bean("authAuthRealm")
	public AuthAuthRealm authAuthRealm() {
		return new AuthAuthRealm();
	}

	@Bean("securityServiceImpl")
	public SecurityServiceImpl securityServiceImpl() {
		return new SecurityServiceImpl();
	}

	@DependsOn("testConductorHasTestCodeDao")
	@Bean("tinyLinkService")
	public TinyLinkService tinyLinkService() {
		return new TinyLinkService();
	}

	@Primary
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		props.put("hbm2ddl.auto", SystemConfigResolver.getProperty("hibernate.hbm2ddl.auto", ""));
		/*
		 * props.put("enableLazyInitialization",
		 * SystemConfigResolver.getProperty("hibernate.enableLazyInitialization",""));
		 * props.put("hibernate.enhancer.enableLazyInitialization",
		 * SystemConfigResolver.getProperty("hibernate.enableLazyInitialization",""));
		 * props.put("hibernate.enhance.enable",
		 * SystemConfigResolver.getProperty("hibernate.enableLazyInitialization",""));
		 */
		props.put("hibernate.enable_lazy_load_no_trans",
				SystemConfigResolver.getProperty("hibernate.enableLazyInitialization", ""));

		props.put("hibernate.dialect", SystemConfigResolver.getProperty("hibernate.dialect", ""));
		props.put("hibernate.generate_statistics",
				SystemConfigResolver.getProperty("hibernate.generate_statistics", ""));
		props.put("hibernate.show_sql", SystemConfigResolver.getProperty("hibernate.show_sql", ""));

		props.put("hibernate.c3p0.acquire_increment",
				SystemConfigResolver.getProperty("hibernate.c3p0.acquire_increment", ""));
		props.put("hibernate.c3p0.idle_test_period",
				SystemConfigResolver.getProperty("hibernate.c3p0.idle_test_period", ""));
		props.put("hibernate.c3p0.max_size", SystemConfigResolver.getProperty("hibernate.c3p0.max_size", ""));
		props.put("hibernate.c3p0.max_statements",
				SystemConfigResolver.getProperty("hibernate.c3p0.max_statements", ""));
		props.put("hibernate.c3p0.min_size", SystemConfigResolver.getProperty("hibernate.c3p0.min_size", ""));
		props.put("hibernate.c3p0.timeout", SystemConfigResolver.getProperty("hibernate.c3p0.timeout", ""));
		props.put("hibernate.default_batch_fetch_size",
				SystemConfigResolver.getProperty("hibernate.default_batch_fetch_size", ""));
		props.put("hibernate.cache.region.factory_class",
				SystemConfigResolver.getProperty("hibernate.cache.region.factory_class", ""));
		props.put("hibernate.cache.use_query_cache",
				SystemConfigResolver.getProperty("hibernate.cache.use_query_cache", ""));
		props.put("hibernate.cache.use_second_level_cache",
				SystemConfigResolver.getProperty("hibernate.cache.use_second_level_cache", ""));
		props.put("hibernate.cache.use_structured_entries",
				SystemConfigResolver.getProperty("hibernate.cache.use_structured_entries", ""));
		props.put("hibernate.cache.use_minimal_puts",
				SystemConfigResolver.getProperty("hibernate.cache.use_minimal_puts", ""));

		props.put("hibernate.connection.driver_class",
				SystemConfigResolver.getProperty("hibernate.connection.driver_class", ""));
		props.put("hibernate.connection.url", SystemConfigResolver.getProperty("hibernate.connection.url", ""));
		props.put("hibernate.connection.username",
				SystemConfigResolver.getProperty("hibernate.connection.username", ""));
		props.put("hibernate.connection.password",
				SystemConfigResolver.getProperty("hibernate.connection.password", ""));

		factoryBean.setHibernateProperties(props);
		factoryBean.setAnnotatedClasses(Candidate.class, College.class, Specialization.class, TestConductor.class,
				UserHasPermission.class, Permission.class, Exam.class, QuestionBank.class, ExamSection.class,
				ExamSectionHasQuestion.class, ExplanationDescription.class, ExamCategory.class, QuestionCategory.class,
				TestConductorLicense.class, TestConductorHasTestCode.class, UserLogin.class, CandidateExam.class,
				CandidateExamSummary.class, MasterSetting.class, ExamSetting.class, QuestionOption.class,
				ExamSettingHasCandidate.class, SicoSectionMarks.class, QuestionDescription.class, QuestionOption.class,PublishExamLicense.class, CandidateQuestionStatus.class, ExamHasSettings.class);

		return factoryBean;
	}

	@Bean("transactionManager")
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	@Scope("prototype")
	public TransactionProxyFactoryBean txAbstractProxy() {
		TransactionProxyFactoryBean transactionProxyFactoryBean = new TransactionProxyFactoryBean();
		transactionProxyFactoryBean.setTransactionManager(transactionManager());

		Properties transactionAttributes = new Properties();
		transactionAttributes.put("*", "PROPAGATION_REQUIRED");
		transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes);
		transactionProxyFactoryBean.setTarget(abstractDao());
		// Finish FactoryBean setup
		transactionProxyFactoryBean.afterPropertiesSet();

		return transactionProxyFactoryBean;
	}

	@Lazy(true)
	@Bean(name = "abstractDaoTarget")
	@Scope("prototype")
	public GenericDao<T, Serializable> abstractDaoTarget(Class<T> type) {

		GenericDaoHibernateImpl<T, Serializable> abstractDaoTarget = new GenericDaoHibernateImpl<T, Serializable>(type);
		abstractDaoTarget.setSessionFactory(sessionFactory().getObject());

		return abstractDaoTarget;
	}

	@Lazy(true)
	@Bean(name = "abstractDao")
	@Scope("prototype")
	public static ProxyFactoryBean abstractDao() {
		return new ProxyFactoryBean();
	}

	@SuppressWarnings("unchecked")
	@Bean("candidateDao")
	public ProxyFactoryBean candidateDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { CandidateDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) Candidate.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("collageDao")
	public ProxyFactoryBean collageDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { CollegeDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) College.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("specializationDao")
	public ProxyFactoryBean specializationDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { SpecializationDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) Specialization.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("testConductorDao")
	public ProxyFactoryBean testConductorDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { TestConductorDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) TestConductor.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("permissionDao")
	public ProxyFactoryBean permissionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { PermissionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) Permission.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("testConductorHasPermissionDao")
	public ProxyFactoryBean testConductorHasPermissionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { UserHasPermissionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) UserHasPermission.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examDao")
	public ProxyFactoryBean examDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) Exam.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examSectionDao")
	public ProxyFactoryBean examSectionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamSectionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamSection.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("questionBankDao")
	public ProxyFactoryBean questionBankDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { QuestionBankDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) QuestionBank.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examSectionHasQuestionDao")
	public ProxyFactoryBean examSectionHasQuestionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamSectionHasQuestionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamSectionHasQuestion.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("explanationDescriptionDao")
	public ProxyFactoryBean explanationDescriptionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExplanationDescriptionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExplanationDescription.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examCategoryDao")
	public ProxyFactoryBean examCategoryDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamCategoryDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamCategory.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("questionCategoryDao")
	public ProxyFactoryBean questionCategoryDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { QuestionCategoryDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) QuestionCategory.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("testConductorLicenceDao")
	public ProxyFactoryBean testConductorLicenceDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { TestConductorLicenseDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) TestConductorLicense.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("testConductorHasTestCodeDao")
	public ProxyFactoryBean testConductorHasTestCodeDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { TestConductorHasTestCodeDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) TestConductorHasTestCode.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("userLoginDao")
	public ProxyFactoryBean userLoginDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { UserLoginDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) UserLogin.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("candidateExamDao")
	public ProxyFactoryBean candidateExamDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { CandidateExamDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) CandidateExam.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("candidateExamSummaryDao")
	public ProxyFactoryBean candidateExamSummaryDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { CandidateExamSummaryDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) CandidateExamSummary.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("masterSettingDao")
	public ProxyFactoryBean masterSettingDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { MasterSettingDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) MasterSetting.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examSettingDao")
	public ProxyFactoryBean examSettingDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamSettingDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamSetting.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("examSettingHasCandidateDao")
	public ProxyFactoryBean examSettingHasCandidateDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamSettingHasCandidateDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamSettingHasCandidate.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}

	@SuppressWarnings("unchecked")
	@Bean("sicoSectionMarksDao")
	public ProxyFactoryBean sicoSectionMarksDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { SicoSectionMarksDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) SicoSectionMarks.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
	
	@SuppressWarnings("unchecked")
	@Bean("questionOptionDao")
	public ProxyFactoryBean questionOptionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { QuestionOptionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) QuestionOption.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
	
	@SuppressWarnings("unchecked")
	@Bean("questionDescriptionDao")
	public ProxyFactoryBean questionDescriptionDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { QuestionDescriptionDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) QuestionDescription.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
	
	@SuppressWarnings("unchecked")
	@Bean("publishExamLicenseDao")
	public ProxyFactoryBean publishExamLicenseDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { PublishExamLicenseDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) PublishExamLicense.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
	
	@SuppressWarnings("unchecked")
	@Bean("candidateQuestionStatusDao")
	public ProxyFactoryBean candidateQuestionStatusDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { CandidateQuestionStatusDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) CandidateQuestionStatus.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
	
	@SuppressWarnings("unchecked")
	@Bean("examHasSettingsDao")
	public ProxyFactoryBean examHasSettingsDao() {
		ProxyFactoryBean pfb = abstractDao();
		try {

			pfb.setProxyInterfaces(new Class<?>[] { ExamHasSettingsDao.class });
			pfb.setTarget(abstractDaoTarget((Class<T>) ExamHasSettings.class));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfb;
	}
}
