package com.dimit.exception;

/**
 * 链未找到异常
 * @author dimit
 */
public class ChainNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5467405828842481793L;

	public ChainNotFoundException() {
		super();
	}

	public ChainNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ChainNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChainNotFoundException(String message) {
		super(message);
	}

	public ChainNotFoundException(Throwable cause) {
		super(cause);
	}

}
