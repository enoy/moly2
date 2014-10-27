package com.gary.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件
 * @author gary
 *
 */
public class SpringMail {
	private static final Logger log = LoggerFactory.getLogger(SpringMail.class);
//	private ApplicationContext ctx = null;
	private JavaMailSender sender = null;
	
	public SpringMail(){
//		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		sender = (JavaMailSender) ctx.getBean("mailSender");
		sender = (JavaMailSender) WebUtil.getBean("mailSender");
	}

	/**
	 * 发送简单邮件
	 * @param to
	 * 		收件人
	 * @param from
	 * 		发件人
	 * @param subject
	 * 		主题
	 * @param template
	 * 		ftl模板
	 * @param param
	 * 		模板参数
	 */
	public void sendSimple(String to, String from, String subject, String template, Map<String, Object> param){
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(to);
		smm.setFrom(from);
		smm.setSubject(subject);
		smm.setText(getText(template,param));
		sender.send(smm);
		log.debug("邮件成功发送到" + to);
	}
	
	/**
	 * 发送邮件
	 * @param to
	 * 		收件人
	 * @param from
	 * 		发件人
	 * @param subject
	 * 		主题
	 * @param template
	 * 		ftl模板
	 * @param param
	 * 		模板参数
	 * @parm inLine
	 * 		inline文件,key:cid,value:java.io.File
	 */
	public void send(String to, String from, String subject, String template, Map<String, Object> param, Map<String, File> inLine){
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(to);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setText(getText(template, param), true);
			for (String key : inLine.keySet()) {
				helper.addInline(key, inLine.get(key));
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sender.send(message);
		log.debug("发送成功" + to);
	}
	
	
	/**
	 * 解析邮件正文内容
	 * @param templateFileName
	 * 		ftl模板文件名
	 * @param param
	 * 		页面参数
	 * @return
	 */
	private String getText(String templateFileName, Map<String, Object> param){
		// ServletActionContext.getServletContext().getRealPath("/")+"template";
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(getClass(), "/template/mail");
		freemarkerCfg.setDefaultEncoding("UTF-8");
		freemarkerCfg.setURLEscapingCharset("UTF-8");
		
		Template t = null;
		try {
			t = freemarkerCfg.getTemplate(templateFileName);
			t.setEncoding("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringWriter writer = new StringWriter();
		try {
			t.process(param, writer);
		} catch (TemplateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return writer.toString();
	}
	
}
