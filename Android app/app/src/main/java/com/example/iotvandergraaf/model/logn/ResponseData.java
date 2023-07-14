package com.example.iotvandergraaf.model.logn;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("success")
    private int codeStatus;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private JsonElement element;

    public ResponseData(int codeStatus, String message) {
        this.codeStatus = codeStatus;
        this.message = message;
    }

    public ResponseData(int codeStatus, String message, JsonElement element) {
        this.codeStatus = codeStatus;
        this.message = message;
        this.element = element;
    }

    public int getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(int codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getElement() {
        return element;
    }

    public void setElement(JsonElement element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "codeStatus=" + codeStatus +
                ", message='" + message + '\'' +
                ", element=" + element +
                '}';
    }
}
