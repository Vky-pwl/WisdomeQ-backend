package com.icat.quest.common.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@Component
public class ConfigInstance {

	private static Configuration cfg = null;
	private static final Logger logger = LoggerFactory.getLogger(ConfigInstance.class);

	public ConfigInstance() {

		cfg = new Configuration();
		try {
			// TODO: -DfreeMarkerDir
			cfg.setDirectoryForTemplateLoading(new File(SystemConfigResolver.getProperty("template_dir", "")));
		} catch (IOException e) {
			logger.error("The template directory for e-mails was not found.");

		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

	}

	public Configuration getInstance() throws IOException {

		return cfg;
	}

}
