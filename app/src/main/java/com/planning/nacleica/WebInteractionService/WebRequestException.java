package com.planning.nacleica.WebInteractionService;

import java.net.ConnectException;

public class WebRequestException {
    public ConnectionErrorTypeEnum ConnectionErrorType;

    public String Message;

    public Exception Exception;


    public WebRequestException(BackgroundResponse result) {
        this.Exception = result.Exception;
        this.Message = this.Exception.getMessage();

        if (this.Exception instanceof ConnectException) {
            this.ConnectionErrorType = ConnectionErrorTypeEnum.SERVER_CONNECTION_ERROR;
        } else {
            this.ConnectionErrorType = ConnectionErrorTypeEnum.EXCEPTION;
        }
    }
    public WebRequestException(Exception ex) {
        this.Exception = ex;
        this.Message = this.Exception.getMessage();
        this.ConnectionErrorType = ConnectionErrorTypeEnum.UNKNOWN;
    }

    public WebRequestException(BackgroundResponse backgroundResponse,ConnectionErrorTypeEnum typeEnum) {
        this.Exception = backgroundResponse.Exception;
        this.Message = this.Exception.getMessage();

        this.ConnectionErrorType = typeEnum;
    }

    public enum ConnectionErrorTypeEnum {
        UNKNOWN,
        SERVER_CONNECTION_ERROR,
        UNAVAILABLE_IP_CONNECTION,
        NO_INTERNET_CONNECTION,
        EXCEPTION
    }

}
