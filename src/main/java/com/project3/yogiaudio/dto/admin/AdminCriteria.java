package com.project3.yogiaudio.dto.admin;

public class AdminCriteria {
	private int page;
	private int pageSize;
	private String searchKeyword;
	private String searchOption;
	private String status;
	
	
	public AdminCriteria() {
		this.page = 1;
		// 한 페이지당 게시글 수
		this.pageSize = 10;
	}
	
	
	 public void setSearchKeyword(String searchKeyword) {
	        this.searchKeyword = searchKeyword;
	 }

	 public String getSearchKeyword() {
	        return searchKeyword;
	 }
	
	
	 
	 public void setSearchOption(String searchOption) {
		    this.searchOption = searchOption;
	 }

	public String getSearchOption() {
		    return searchOption;
	 }
	 
    // status 필드에 대한 Setter 메서드 추가
    public void setStatus(String status) {
        this.status = status;
    }

    // status 필드에 대한 Getter 메서드 추가
    public String getStatus() {
        return status;
    }
	 
	// alt shift s + r

	// 페이지를 설정한다
	public void setPage(int page) {
		if(page <=0) {
			this.page = 1;
			return;
		}
		this.page = page;		
	}
	public void setPageSize(int pageSize) {
		
		if(pageSize <= 0 || pageSize > 100) {
			this.pageSize = 10;
			return;
		}
		
		this.pageSize = pageSize;
	}
	
	
	public int getPage() {
		return page;
	}
	public int getPageSize() {
		return pageSize;
	}
	
	//private int startPage; // 변수선언없이 get메서드만 구현
	// 변수는 없지만, mapper에서 사용 # {startPage} 요소 호출
	// 속성값으로 정의 안되어 있는데 쿼리문에 매핑이 되네?
	// 인덱스번호 메서드
	public int getOffset() {
		// 페이지 정보를 쿼리사용되는 값(시작인덱스)으로 변경
		// 1페이지를 불러오고 싶으면 인덱스 0번, 2페이지를 불러오고 싶으면 인덱스 10번
		return (this.page - 1) * pageSize;
	}

	
	
	// alt shift s + s
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", pageSize=" + pageSize + "]";
	}

}