package ru.kilai.exeptions;

public class BedRequestException extends RuntimeException {

    public BedRequestException() {
        super();
    }

    public BedRequestException(String testMsg) {
        super(testMsg);
    }

    public BedRequestException(Throwable cause) {
        super(cause);
    }
}
