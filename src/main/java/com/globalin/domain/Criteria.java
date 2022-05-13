package com.globalin.domain;

// 페이지 정보를 담고 있었는데
// 검색 정보까지 담도록 수정
public class Criteria {

	private int pageNum, amount;

	// 검색할 타입(이름, 작성자, 내용 등등), 검색할 키워드
	// 타입이 여러개일 경우 처리가 필요하다
	// 제목 : T, 작성자 : W, 내용 : C
	// 제목 + 내용 TC / 작성자 + 내용 WC
	// 전부 초함 TWC
	// 복합 조건일 때를 대비해서 문자열을 쪼갠 다음 배열로 리턴해준다
	private String type, keyword;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Criteria() {
		// 첫 페이지 번호는 1, 기본적으로 한페이지에 10개씩
		this.pageNum = 1;
		this.amount = 10;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", type=" + type + ", keyword=" + keyword + "]";
	}

	// 타입을 쪼개서 문자열 배열로 만들어서 리턴해주는 메소드
	// type="TWC"
	// String[] typeArr = { "T", "W", "C" };
	public String[] getTypeArr() {
		String[] result = {};
		if (type == null) {
			// type이 null이면 검색 조건이 없다
			// 빈 배열을 리턴해준다
		} else {
			// type이 null이 아니면 검색 조건이 하나 이상 있다
			// type을 쪼개서 배열로 만들어준다
			result = type.split("");
		}
		return result;
		// return result == null ? new String[] {} : type.split("");
	}

}
