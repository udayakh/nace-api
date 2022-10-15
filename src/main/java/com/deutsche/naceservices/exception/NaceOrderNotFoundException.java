package com.deutsche.naceservices.exception;

public class NaceOrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 799534487624728918L;

	public NaceOrderNotFoundException() {
		super();
	}

	public NaceOrderNotFoundException(String message) {
		super(message);
	}
}