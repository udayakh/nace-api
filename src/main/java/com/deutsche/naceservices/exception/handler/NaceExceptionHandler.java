package com.deutsche.naceservices.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.deutsche.naceservices.exception.NaceOrderNotFoundException;
import com.deutsche.naceservices.response.ResponseMessage;

@RestControllerAdvice
public class NaceExceptionHandler {
	@ExceptionHandler(NaceOrderNotFoundException.class)
	public ResponseEntity<ResponseMessage> handleNaceOrderNotFoundException(NaceOrderNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File size exceeded the allowed limit"));
	}
}