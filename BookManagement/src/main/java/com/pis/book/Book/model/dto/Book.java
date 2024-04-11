package com.pis.book.Book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor // 기본 생성자
@ToString
@Builder
@AllArgsConstructor
public class Book {
	private int bookNo;
	private String bookTitle;
	private String bookWriter;
	private int bookPrice;
	private String regDate;
	

}
