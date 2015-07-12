package cn.hw.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.hw.domain.Tuser;
import cn.hw.service.UserService;


public class TestConfigFramework {
	@Test
	public void test_Spring() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "classpath:spring.xml","classpath:spring-hibernate.xml" });// 表示根目录
		UserService us = (UserService) ac.getBean("userService");
		us.test();
	}

	@Test
	public void test1(){
		System.out.println("1");
	}
	@Test
	public void test_Hibernate_save() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {
				"classpath:spring.xml", "classpath:spring-hibernate.xml" });// 表示根目录
		UserService us = (UserService) ac.getBean("userService");
		Tuser t = new Tuser();
		t.setId(UUID.randomUUID().toString());
		t.setName("hw");
		t.setCreatTime(new Date());
		t.setPwd("111111");
		us.save(t);
	}
}
