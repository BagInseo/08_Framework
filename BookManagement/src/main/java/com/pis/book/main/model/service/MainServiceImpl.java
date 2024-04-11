package com.pis.book.main.model.service;

import org.springframework.stereotype.Service;

import com.pis.book.main.model.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	private final MainMapper mapper;

}
