package com.example.libraryreservation.response;

public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");
    final int statusCode;
    final String code;

    public Integer getStatusCode() {
        return statusCode;
    }

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
