package com.gary.framework.dao;

import org.junit.Test;

import com.gary.framework.util.BaseTest;

public class SysMenuDAOTest extends BaseTest {

	@Test
	public void testUpdateSubMenuSort(){
		daoManager.getSysMenuDAO().updateSubMenuSort(5);
	}
}
