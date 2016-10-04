package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.util.Json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ibm.aix.Helpers.*;

/**
 * Created by joe on 9/27/16.
 */

public class Routing {
    public static String makeCFuncCall(PCMLPath pcmlPath, String inputContent) {
        ObjectMapper mapper = Json.mapper();

        DatagramSocketInfo dInfo = connectUDP(pcmlPath.getRoutingInfo().getHost(), pcmlPath.getRoutingInfo().getPort());

        if (dInfo == null) {
            System.err.println("Error connecting to server");
            return "{}";
        }

        CallServiceMessage csm = new CallServiceMessage(
                pcmlPath.getPcmlInfo().getLibrary(),
                pcmlPath.getPcmlInfo().getPcmlId(),
                pcmlPath.getPcmlInfo().getSymbol());


        try {
            JsonNode paramNode = mapper.createObjectNode();
            if (!inputContent.equals("")) {
                paramNode = mapper.readTree(inputContent);
            }

            ObjectNode newMetadata = (ObjectNode)  paramNode;

            //JsonNode returnEntry = mapper.readTree("{\"__returnCode\":{}}");
            JsonNode returnEntry = mapper.readTree(pcmlPath.getReturnEntries().toString());

            // JsonNode staticValues = mapper.readTree("{\"e\":10}");
            JsonNode staticValues = pcmlPath.getStaticValues();
            if (staticValues != null) {
                newMetadata.setAll((ObjectNode) staticValues);
            }

            //newMetadata.putObject(staticValues.toString());
            csm.setParams(newMetadata);
            csm.setReturnEntries(returnEntry);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "";
        }
        String jsonString;

        try {
            jsonString = mapper.writeValueAsString(csm);
        } catch (Exception e) {
            System.err.println(e);
            jsonString = "Error creating message";
        }

        sendMessageUDP(dInfo, jsonString);

        String message = receiveMessageUDP(dInfo);

        dInfo.getDsocket().close();

        return message;
    }
}
