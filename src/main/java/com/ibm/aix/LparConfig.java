package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.util.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ibm.aix.Helpers.connect;
import static com.ibm.aix.Helpers.receiveMessage;
import static com.ibm.aix.Helpers.sendMessage;

/**
 * Created by joe on 8/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LparConfig {
    private String hostname;
    private String processorFamily;
    private String OSBuild;
    private long drives;
    private int lcpus;

    private native void getConfig();

    public static LparConfig getLparConfig(InputFormat input) {
        LparConfig lp = new LparConfig();

        connect("127.0.0.1", 4000);

        CallServiceMessage csm = new CallServiceMessage(
                "libabc.so",
                "id71S2v0.1",
                "squareabcdptr");

        Map<String, FunctionParams> params = new HashMap<String, FunctionParams>();
        Map<String, FunctionParams> returnEntries = new HashMap<String, FunctionParams>();

        FunctionParams rc = new FunctionParams("returnObj", "struct");
        rc.setSubTypeId("structabc");
        returnEntries.put("__returnCode", rc);

        //csm.setReturnEntries(returnEntries);

        ObjectMapper mapper = Json.mapper();

        String jsonString;

        try {
            jsonString = mapper.writeValueAsString(csm);
        } catch (Exception e) {
            System.err.println(e);
            jsonString = "Error creating message";
        }

        sendMessage(jsonString);

        String message = receiveMessage();
        try {
            lp = mapper.readValue(message, LparConfig.class);
        } catch (IOException e) {
            System.err.println(e);

        }
        return lp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getProcessorFamily() {
        return processorFamily;
    }

    public void setProcessorFamily(String processorFamily) {
        this.processorFamily = processorFamily;
    }

    public String getOSBuild() {
        return OSBuild;
    }

    public void setOSBuild(String OSBuild) {
        this.OSBuild = OSBuild;
    }

    public long getDrives() {
        return drives;
    }

    public void setDrives(long drives) {
        this.drives = drives;
    }

    public int getLcpus() {
        return lcpus;
    }

    public void setLcpus(int lcpus) {
        this.lcpus = lcpus;
    }


}

