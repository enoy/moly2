package com.gary.framework.util;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gary.framework.dao.DAOManager;
import com.gary.framework.service.ServiceManager;

public class BaseTest {
	
	public static ClassPathXmlApplicationContext factory = null;
	public static DataSource dataSource = null;
	protected static Logger log = Logger.getLogger(BaseTest.class);
	protected static DAOManager daoManager = null;
	protected static ServiceManager serviceManager = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			factory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
			daoManager = (DAOManager) factory.getBean("daoManager");
			serviceManager = (ServiceManager) factory.getBean("serviceManager");
		}catch(Exception e){
			e.printStackTrace();
		}
		dataSource = (DataSource) factory.getBean("dataSource");
		log.info("=======测试开始=======");
		log.debug(dataSource == null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log.info("=======测试完成=======");
		factory.close();
	}
	
	protected QueryRunner getQueryRunner(){
		return new QueryRunner(dataSource);
	}
}
