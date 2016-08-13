package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
                "/usr/lib/libperfstat.a",
                "function1",
                "LparConfig");

//        FunctionParams fp1 = new FunctionParams("param1", "int");
//        fp1.setValues(new String[]{"10"});
//
//        FunctionParams fp2 = new FunctionParams("param2", "int");
//        fp2.setCount(3);
//        fp2.setValues(new String[]{"10", "20", "30"});
//        fp2.setPassBy(1);
//
//        FunctionParams fp3 = new FunctionParams("param3", "struct");
//        fp3.setSubTypeId("structabc");
//        fp3.setCount(2);
//        fp3.setPassBy(1);
//        fp3.setValues(new String[]{
//                "20,'str1','str2','str3','str4'",
//                "34,'str321','str243','str334','str434'"
//        });
//
//        FunctionParams fp4 = new FunctionParams("param4", "struct");
//        fp4.setPassBy(1);
//        fp4.setCount(4);
//        fp4.setOutput(true);
//        fp4.setSubTypeId("structabc");
//
//        FunctionParams fp5 = new FunctionParams("param5", "int");
//        fp5.setValues(new String[]{"10"});
//        fp5.setPassBy(1);
//
//        FunctionParams fp6 = new FunctionParams("param6", "struct");
//        fp6.setPassBy(1);
//        fp6.setOutput(true);
//        fp6.setCountRef("param5");
//        fp6.setSubTypeId("structabc");
//
//
//        csm.setParams(new FunctionParams[]{fp1, fp2, fp3, fp4, fp5, fp6});
//        csm.setReturnCode(rc);

        csm.setParams(input.getParamaters());

        FunctionParams rc = new FunctionParams("returnObj", "struct");
        rc.setSubTypeId("structabc");

        csm.setReturnCode(rc);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString;

        try {
            jsonString = mapper.writeValueAsString(csm);
        } catch (Exception e) {
            System.err.println(e);
            jsonString = "Error creating message";
        }

        // String [] fp1_values = new String[1];
        // fp1_values[0] = "10";
        // fp1.setValues(fp1_values);

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

