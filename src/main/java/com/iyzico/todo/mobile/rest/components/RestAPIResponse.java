package com.iyzico.todo.mobile.rest.components;

public class RestAPIResponse {

	private boolean success = false;
	private String message;
    private Object data;

    public static RestAPIResponse ok(Object data, String message) {
        return new RestAPIResponse(Boolean.TRUE, message, data);
    }

    public static RestAPIResponse ok(Object data) {
        return new RestAPIResponse(Boolean.TRUE, "success", data);
    }

    public static RestAPIResponse error(Object data, String message) {
        return new RestAPIResponse(Boolean.FALSE, message, data);
    }

    public static RestAPIResponse error(String message) {
        return new RestAPIResponse(Boolean.FALSE, message, null);
    }
    
    public RestAPIResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public RestAPIResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestAPIResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RestAPIResponse setData(Object data) {
        this.data = data;
        return this;
    }

	@Override
	public String toString() {
		return "RestAPIResponse [success=" + success + ", message=" + message + ", data=" + data + "]";
	}
    
}

