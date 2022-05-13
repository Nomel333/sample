package com.globalin.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.globalin.domain.BoardVO;
import com.globalin.domain.Criteria;
import com.globalin.domain.Page;
import com.globalin.service.BoardService;

@Controller // 스프링에서 componentScan으로 인식할 수 있도록
// 이 컨트롤러가 어떤 경로 요청을 처리하는지 설정해주는 어노테이션
@RequestMapping("/board/*")
public class BoardController {

	// 컨트롤러에서 구현해놓은 비즈니스 로직을 사용하기 위해 필요한 객체
	// service가 필요
	@Autowired
	private BoardService service;

	// 로그
	private static Logger log = LoggerFactory.getLogger(BoardController.class);

	// 전체 리스트 조회 기능
	// 페이지 처리가 된 list를 가져오도록 변경
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		// requst에 Attribute를 담았던 것처럼 할수 있다.
		log.info("list : " + cri);
		model.addAttribute("list", service.getList(cri));
		int total = service.getTotal(cri);
		log.info("total : " + total);
		model.addAttribute("pageMaker", new Page(cri, total));
	}

	// 등록 기능
	// form에서 사용자가 게시물 정보를 입력하고 작성 요청을 보냄
	// 보내고 나서 화면을 전환-> 리스트 화면으로 돌아가도록
	// 리스트 화면으로 돌아가면서 방금 작성한 게시물의 번호를 담는다
	// 리다이렉트로 보내주기
	@PostMapping("/register")
	// 인증이 된 사용자만 게시글 작성이 가능하도록 설정 추가
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register : " + board);

		service.register(board);
		// 리다이렉트하면 요청 정보가 사라진다
		// 1화용 정보를 담아주는 flashAttribute를 사용
		rttr.addFlashAttribute("result", board.getBno());
		// response.sendRedirect();
		// response 객체가 없어서 직접 사용하지 못하니까
		// return 할 string에 redirect한다고 표시

		return "redirect:/board/list";
	}

	// 하나 가져오기
	// 파라미터로 가져올 게시물의 번호가 필요
	// 번호를 통해서 db에서 조회한 게시물의 정보를 전달
	// 데이터 전달자인 Model을 사용해서 전달
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") int bno, Criteria cri, Model model) {
		log.info(" get or modify : " + bno);
		log.info("cri : " + cri);
		// 게시물의 정보를 전달
		// 서비스에서 board 정보 가져온 다음
		// model에 넣어주면 된다
		BoardVO board = service.get(bno);
		model.addAttribute("cri", cri);
		model.addAttribute("board", board);
	}

	// 수정 작업 이후에
	// 리스트 페이지로 돌아가게 한다
	// redirect
	// 수정 하려면 필요한 파라미터
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		if (service.modify(board) == true) {
			// 1회용 속성이 flashAttribute 추가
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}

	// 스프링 시큐리티를 이용해서 로그인한 사용자 == 게시물 작성자 일때만 삭제 가능하도록 함
	// #parameter를 사용할 수 있다
	// #(input 태그의 name 속성) 대신 메소드의 파라미터로 받아와야한다
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") int bno, Criteria cri, RedirectAttributes rttr, String writer) {

		log.info("remove : " + bno);
		if (service.remove(bno) == true) {
			// 1회용 속성이 flashAttribute 추가
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}

	@GetMapping("/register")
	// 로그인한 사용자만 게시글 등록 페이지로 갈 수 있도록 한다
	@PreAuthorize("isAuthenticated()")
	public void register() {
		// 메소드 리턴 타입이 void -> url 이름이 jsp 파일이름
		// board/register 요청을 보내면
		// register.jsp 파일을 찾아서 사용자한테 보여준다
	}

//	@GetMapping("/modify")
//	public void Modify(@RequestParam("bno") int bno, Model model) {
//		log.info("modify : " + bno);
//		// 게시물의 정보를 전달
//		// 서비스에서 board 정보 가져온 다음
//		// model에 넣어주면 된다
//		BoardVO board = service.get(bno);
//		model.addAttribute("board", board);
//	}
}