package io.datajek.spring;

import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EbookErrorResponse {
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private ZonedDateTime timestamp;
	private int statusCode;
	private String path;
	private String message;

	public EbookErrorResponse(ZonedDateTime timestamp, int statusCode, String path, String message) {
		super();
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.path = path;
		this.message = message;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}