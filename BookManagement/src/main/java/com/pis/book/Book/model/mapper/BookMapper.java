package com.pis.book.Book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pis.book.Book.model.dto.Book;

@Mapper
public interface BookMapper {


	/**책 목록 조회하기
	 * @return
	 */
	List<Book> selectBookList();

	/** 책 등록하기
	 * @param book 
	 * @return result
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

	/**책 삭제
	 * @param bookNo
	 * @return
	 */
	int deleteBook(int bookNo);

}
