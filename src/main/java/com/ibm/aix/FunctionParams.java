package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

/**
 * Created by joe on 8/13/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class FunctionParams {
    @NotNull
    private String name;
    @NotNull
    private String type;
    private long count;
    private String countRef;
    private int passBy;
    private String []values;
    private boolean output;
    private String subTypeId;

    public FunctionParams() {

    }

    public FunctionParams(String name, String type) {
        this.name = name;
        this.type = type;
        this.count = 1;
        this.passBy = 0;
        this.output = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getCountRef() {
        return countRef;
    }

    public void setCountRef(String countRef) {
        this.countRef = countRef;
    }

    public int getPassBy() {
        return passBy;
    }

    public void setPassBy(int passBy) {
        this.passBy = passBy;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }



}
