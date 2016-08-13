package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Random;

/**
 * Created by joe on 8/13/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallServiceMessage {
    long id;
    String libraryWithPath;
    String symbol;
    String outputClass;
    FunctionParams[]params;
    FunctionParams returnCode;

    public CallServiceMessage(String libraryWithPath, String symbol, String outputClass) {
        Random gen = new Random(100000);
        id = gen.nextLong();
        this.libraryWithPath = libraryWithPath;
        this.symbol = symbol;
        this.outputClass = outputClass;
    }

    public CallServiceMessage(String libraryWithPath, String symbol) {
        Random gen = new Random(100000);
        id = gen.nextLong();
        this.libraryWithPath = libraryWithPath;
        this.symbol = symbol;
        this.outputClass = symbol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibraryWithPath() {
        return libraryWithPath;
    }

    public void setLibraryWithPath(String libraryWithPath) {
        this.libraryWithPath = libraryWithPath;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOutputClass() {
        return outputClass;
    }

    public void setOutputClass(String outputClass) {
        this.outputClass = outputClass;
    }

    public FunctionParams[] getParams() {
        return params;
    }

    public void setParams(FunctionParams[] params) {
        this.params = params;
    }

    public FunctionParams getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(FunctionParams returnCode) {
        this.returnCode = returnCode;
    }
}
