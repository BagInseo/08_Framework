package com.pis.book.Book.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pis.book.Book.model.dto.Book;
import com.pis.book.Book.model.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	private final BookMapper mapper;
	
	
	
	
	//책 목록 조회하기
	@Override
	public List<Book> selectBookList() {
		return mapper.selectBookList();
	}
	
	
	
	
	
	//책 등록하기
	@Override
	public int addBook(Book book) {
		return mapper.addBook(book);
	}
	
	
@Override
	public List<Book> SearchUpdateDelete(String bookTitle) {
		return mapper.SearchUpdateDelete(bookTitle);
	}



//가격 수정하기
@Override
public int updatePrice(Book book) {
	return mapper.updatePrice(book);
}

//책 삭제하기
@Override
public int deleteBook(int bookNo) {
	return mapper.deleteBook(bookNo);
}

}
