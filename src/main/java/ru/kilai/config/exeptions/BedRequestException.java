package ru.kilai.config.exeptions;

public class BedRequestException extends RuntimeException{

    public BedRequestException() {
        super();
    }

    public BedRequestException(String testMsg) {
        super(testMsg);
    }
}
