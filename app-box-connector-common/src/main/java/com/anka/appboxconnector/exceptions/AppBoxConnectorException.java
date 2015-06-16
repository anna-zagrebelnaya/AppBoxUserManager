package com.anka.appboxconnector.exceptions;

public class AppBoxConnectorException extends RuntimeException {
    public String response;
    public int responseCode;

    public String getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public AppBoxConnectorException(int responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public AppBoxConnectorException(int responseCode, String response, String message) {
        super(message);
        this.response = response;
        this.responseCode = responseCode;
    }
}
