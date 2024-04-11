package com.pis.book.Book.controller;

import java.lang.reflect.Array;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pis.book.Book.model.dto.Book;
import com.pis.book.Book.model.service.BookService;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@RequestMapping("book")
@Slf4j
public class BookController {
	private final BookService service;
	
	
	
	
	/** 책 목록 조회하기
	 * @return
	 */
	@ResponseBody
	@GetMapping("selectBookList")
	public List<Book> selectBookList() {
		
		return service.selectBookList();
	}
	
	
	
	/**책 등록 페이지로 이동
	 * @return
	 */
	@GetMapping("addBook")
	public String addBook() {
		return "book/addBook";
	}
	
	
	/**수정,삭제,검색 페이지로 이동
	 * @return
	 */
	@GetMapping("SearchUpdateDelete")
	public String SearchUpdateDelete() {
		return "book/SearchUpdateDelete";
	}
	
	
	/** 책 등록
	 * @param book
	 * @param ra
	 * @return
	 */
	@PostMapping("addBook")
	public String addBook(
			Book book,
			RedirectAttributes ra) {
		
		int result = service.addBook(book);
		
		
		String message=null;

		if(result>0) {//등록 성공
			message="등록 성공!";
		}else {
			message="등록 실패";

		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/";
	}
	
	
	/** 책 제목 검색하기
	 * @return
	 */
	@PostMapping("SearchUpdateDelete")
	@ResponseBody
	public List<Book> SearchUpdateDelete(
			@RequestBody String bookTitle ) {
		
		return service.SearchUpdateDelete(bookTitle); 
	}
	
	
	
	/** 가격 수정하기
	 * @return result
	 */
	@ResponseBody
	@PutMapping("updatePrice")
	public int updatePrice(@RequestBody Book book) {
		return service.updatePrice(book);
		
	}
	
	
	@ResponseBody
	@DeleteMapping("delete")
	public int deleteBook(@RequestBody int bookNo) {
		return service.deleteBook(bookNo);
	}
	

}
