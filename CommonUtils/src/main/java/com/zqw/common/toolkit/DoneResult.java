package com.zqw.common.toolkit;

public class DoneResult<T> {

	private boolean successful;

	private T result;

	public DoneResult() {

	}

	public DoneResult(boolean successful, T result) {
		this.successful = successful;
		this.result = result;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}