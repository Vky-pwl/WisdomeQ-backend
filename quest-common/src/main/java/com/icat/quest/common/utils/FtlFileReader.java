package com.icat.quest.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class FtlFileReader {
	
	@Autowired
	private ConfigInstance		configInstance;

	public String read(String templateName, Map<String, Object> tagValueMap) {

		Template template = null;
		try {
			Configuration cfg = configInstance.getInstance();
			String filePath = templateName + ".ftl";
			template = cfg.getTemplate(filePath);

		} catch (IOException e) {

			e.printStackTrace();
		}

		
		Writer messageContent = new StringWriter();
		try {
			template.process(tagValueMap, messageContent);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prepareHtmlFormat(messageContent.toString());

	}
	
	private String prepareHtmlFormat(String messageBody) {

		StringBuilder messageBuilder = new StringBuilder();
		boolean previousASpace = false;
		for (char singleChar : messageBody.toCharArray()) {
			if (singleChar == ' ') {
				if (previousASpace) {
					messageBuilder.append("&nbsp;");
					previousASpace = false;
					continue;
				}
				previousASpace = true;
			} else {
				previousASpace = false;
			}
			switch (singleChar) {
			case '<':
				messageBuilder.append("&lt;");
				break;
			case '>':
				messageBuilder.append("&gt;");
				break;
			case '&':
				messageBuilder.append("&amp;");
				break;
			case '"':
				messageBuilder.append("&quot;");
				break;
			case '\n':
				messageBuilder.append("<br>");
				break;

			// We need Tab support here, because we print StackTraces as HTML
			case '\t':
				messageBuilder.append("&nbsp; &nbsp; &nbsp;");
				break;
			default:
				if (singleChar < 128) {
					messageBuilder.append(singleChar);
				} else {
					messageBuilder.append("&#").append((int) singleChar).append(";");
				}
			}
		}
		return messageBuilder.toString();
	}


	
}
