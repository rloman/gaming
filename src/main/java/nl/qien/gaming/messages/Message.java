package nl.qien.gaming.messages;

import java.io.Serializable;

public class Message implements Serializable {

    private String message;
    private int httpStatus;

    public Message(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Message() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
