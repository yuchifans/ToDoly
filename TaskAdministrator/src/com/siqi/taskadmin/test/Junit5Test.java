package com.siqi.taskadmin.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.siqi.taskadmin.util.DataUtil;
import com.siqi.taskadmin.model.Task;



/**
 * This class is unit test class for the "ToDoLy" application
 * 
 * @author Siqi Qian
 * @version 2018.10.09
 */
class Junit5Test {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void dataUtilUnitTest() {
		assertEquals("12-12-2012", DataUtil.dateToString(DataUtil.createDate("12-12-2012")));
		assertEquals("01-02-0001", DataUtil.dateToString(DataUtil.createDate("1-2-1")));
		
		assertFalse(DataUtil.isInteger("a"));
		assertTrue(DataUtil.isInteger("1"));
		assertTrue(DataUtil.isInteger("  1"));
		assertTrue(DataUtil.isInteger("1    "));
		assertTrue(DataUtil.isStatus("0"));
		assertTrue(DataUtil.isStatus("1"));
		assertFalse(DataUtil.isStatus("2"));
		assertTrue(DataUtil.isStatus(" 0   "));
		assertTrue(DataUtil.isStatus(" 1    "));
		assertFalse(DataUtil.isInteger(" 1-1 "));
		assertFalse(DataUtil.isInteger(Integer.toString(Integer.MAX_VALUE+1)));
		assertFalse(DataUtil.isInteger("2147483648"));
		assertFalse(DataUtil.isStatus("2147483648"));
		
		
	}
	
	@Test
	public void TaskUnitTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Task task =new Task();
		Class<?> clazz=task.getClass();
		Field titleField =clazz.getDeclaredField("title");
		Field projectNameField = clazz.getDeclaredField("projectName");
		titleField.setAccessible(true);
		projectNameField.setAccessible(true);
		String title=(String)titleField.get(task);
		String projectName=(String)projectNameField.get(task);
		
		
		assertEquals(null,title);
		assertEquals(null,projectName);
		
		task.setTitle("title1");
		title =(String)titleField.get(task);
		assertEquals("title1",title);
		assertEquals("title1",task.getTitle());
		
		
		task.setProject("project1");
		projectName =(String)projectNameField.get(task);
		assertEquals("project1",projectName);
		assertEquals("project1",task.getProject());
		
		
	}

}
