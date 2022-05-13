package com.globalin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalin.mapper.Sample1Mapper;
import com.globalin.mapper.Sample2Mapper;
@Transactional
@Service
public class SampleTxServiceImpl implements SampleTxService {

	// 로그
	private Logger log = LoggerFactory.getLogger(SampleTxServiceImpl.class);

	@Autowired
	private Sample1Mapper mapper1;

	@Autowired
	private Sample2Mapper mapper2;

	// 이 메소드에 트랜잭션 처리를 하겠다고 선언
	// 이 메소드 실행 도중 예외가 발생하거나 기능을 실행하는데 실패하면 
	// 실행 결과를 모두 롤백해서 없던 것으로 간주한다
	// 반대로 ㄷ 성공시 그대로 성공하도록 한다
	@Transactional
	@Override
	public void addData(String value) {

		log.info("mapper1......");
		mapper1.insertCol1(value);

		log.info("mapper2......");
		mapper2.insertCol2(value);
		
		log.info("end..........");
		// 원하던 결과 -> 하나가 안되면 다 안 되거나 OR 다 되거나
	}

}
