package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;
import java.util.Random;

/**
 * Created by joe on 8/13/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallServiceMessage {
    long id;
    String pcmlId;
    String library;
    String symbol;
    JsonNode params;
    JsonNode returnEntries;

    public CallServiceMessage(String library, String pcmlId, String symbol) {
        Random gen = new Random(1000);
        id = gen.nextInt(10000);
        this.library = library;
        this.pcmlId = pcmlId;
        this.symbol = symbol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPcmlId() {
        return pcmlId;
    }

    public void setPcmlId(String pcmlId) {
        this.pcmlId = pcmlId;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public JsonNode getParams() {
        return params;
    }

    public void setParams(JsonNode params) {
        this.params = params;
    }

    public JsonNode getReturnEntries() {
        return returnEntries;
    }

    public void setReturnEntries(JsonNode returnEntries) {
        this.returnEntries = returnEntries;
    }
}
