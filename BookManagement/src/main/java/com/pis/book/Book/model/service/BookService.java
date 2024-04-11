package com.pis.book.Book.model.service;

import java.util.List;

import com.pis.book.Book.model.dto.Book;

public interface BookService {

	/** 책 목록 조회 하기
	 * @return
	 */
	List<Book> selectBookList();

	/** 책 등록
	 * @param book
	 * @return
	 */
	int addBook(Book book);

	/** 책 제목 검색하기
	 * @param bookTitle
	 * @return
	 */
	List<Book> SearchUpdateDelete(String bookTitle);

	

	/** 가격 수정하기
	 * @param book
	 * @return
	 */
	int updatePrice(Book book);

	/** 책 삭제
	 * @param bookNo
	 * @return
	 */
	int deleteBook(int bookNo);

}
