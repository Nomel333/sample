package com.globalin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SampleTxServiceTest {

	@Autowired
	private SampleTxService service;

	@Test
	public void testAddData() {

		String data = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세";

		System.out.println(data.getBytes().length);
		service.addData(data);

	}

}
