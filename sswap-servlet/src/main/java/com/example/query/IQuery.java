package com.example.query;

import java.time.LocalDate;

import com.example.RequestParams;

public interface IQuery {
	public String generateQuery(RequestParams params);
	
	public String generateQuery(RequestParams params, LocalDate startDate, LocalDate endDate);
}
