package me.josephzhu.spring101webmvc.framework.exception;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class ApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiException(Throwable cause, String errorCode, String errorMessage) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
