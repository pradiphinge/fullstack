package com.mpscstarter.exceptions;

public class S3Exception extends RuntimeException {

	public S3Exception(Throwable e) {
		super();
	}
	
	public S3Exception(String s) {
		super(s);
	}
	
}
