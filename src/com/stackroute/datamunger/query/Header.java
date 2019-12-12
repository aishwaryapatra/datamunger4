package com.stackroute.datamunger.query;

public class Header {

String[] arr;
		/*
	 * This class should contain a member variable which is a String array, to hold
	 * the headers.
	 */
	
	public Header(String[] arr) {
		super();
		this.arr = arr;
	}
	public String[] getHeaders() {
		return this.arr;
	}
	public void setHeaders(String[] arr) {
		this.arr=arr;
	}
}