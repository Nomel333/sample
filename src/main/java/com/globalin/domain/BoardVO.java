package com.globalin.domain;

import java.util.Date;

// 테이블 컬럼 구조를 반영하는 클래스
// tbl_board

public class BoardVO {

	private int bno, replyCnt;
	private String title, content, writer;
	private Date regDate, updateDate;

	public int getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", replyCnt=" + replyCnt + ", title=" + title + ", content=" + content
				+ ", writer=" + writer + ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}

}
