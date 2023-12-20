package com.example.demo.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Page<T> {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer limit;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer offset;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer total;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer current;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer pages;
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	private ArrayList<T> results;
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public ArrayList<T> getResults() {
		return results;
	}
	public void setResults(ArrayList<T> results) {
		this.results = results;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}	
}
