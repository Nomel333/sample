package com.globalin.ex02;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globalin.domain.BoardVO;
import com.globalin.domain.Criteria;
import com.globalin.mapper.BoardMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTest {

	@Autowired
	private BoardMapper mapper;

//	@Test
//	public void testGetList() {
//		List<BoardVO> list = mapper.getList();
//		for (BoardVO b : list) {
//			System.out.println(b);
//		}
//	}

//	@Test
//	public void insertTest() {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 글");
//		board.setContent("새로 작성하는 내용");
//		board.setWriter("newbie");
//
//		mapper.insert(board);
//
//	}

//	@Test
//	public void testInsertSelectKey() {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 select key");
//		board.setContent("새로 작성하는 내용 select key");
//		board.setWriter("newbie");
//
//		mapper.insertSelectKey(board);
//		System.out.println(board);
//	}

//	@Test
//	public void testRead() {
//		BoardVO board = mapper.read(3);
//		System.out.println(board);
//	}

//	@Test
//	public void deleteTest() {
//		int res = mapper.delete(0);
//		System.out.println(res);
//	}

//	@Test
//	public void updateTest() {
//		BoardVO board = mapper.read(1);
//		System.out.println(mapper.update(board));
//	}

	@Test
	public void testPaging() {

		// 페이지 정보 생성
		Criteria cri = new Criteria();
		cri.setKeyword("newbi");
		cri.setType("W");

		List<BoardVO> list = mapper.getListWithPaging(cri);
		
		for(BoardVO board : list) {
			System.out.println(board);
		}
	}

}
