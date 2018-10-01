package com.ecom.exception;

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279753943844867702L;
	private String str;
	public ApplicationException(String str, Throwable e) {
		this.str = str;
	}
	
	public String toString() {
		return str;
	}
}
