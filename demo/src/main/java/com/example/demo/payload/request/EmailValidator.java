package com.example.demo.payload.request;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;


@Service
public class EmailValidator implements Predicate<String> {

	@Override
	public boolean test(String t) {
		
		return true;
	}
	
}
