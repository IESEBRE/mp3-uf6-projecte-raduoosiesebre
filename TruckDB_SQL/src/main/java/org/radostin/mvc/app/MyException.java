package org.radostin.mvc.app;

import java.util.Map;

import static java.util.Map.entry;

public class MyException extends Exception{
    private int code;
    private String message;

    public MyException(int code, String message){
        this.code = code;
        this.message = message;

        final Map<Integer, String> listExceptions = Map.ofEntries(
                entry(1, "Error of input and output"),
                entry(2, "Error reading the file"),
                entry(3, "Error writing the file"),
                entry(4, "Error to save"),
                entry(5, "Error to save and close"),
                entry(6, "Error to insert fields"),
                entry(7, "Error to modify fields"),
                entry(8, "Error to delete fields")
        );
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
