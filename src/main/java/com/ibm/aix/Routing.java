package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.util.Json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ibm.aix.Helpers.connect;
import static com.ibm.aix.Helpers.receiveMessage;
import static com.ibm.aix.Helpers.sendMessage;

/**
 * Created by joe on 9/27/16.
 */

public class Routing {
    public static String makeCFuncCall(PCMLPath pcmlPath, String inputContent) {
        ObjectMapper mapper = Json.mapper();

        connect(pcmlPath.getRoutingInfo().getHost(), pcmlPath.getRoutingInfo().getPort());


        CallServiceMessage csm = new CallServiceMessage(
                pcmlPath.getPcmlInfo().getLibrary(),
                pcmlPath.getPcmlInfo().getPcmlId(),
                pcmlPath.getPcmlInfo().getSymbol());


        try {
            JsonNode paramNode = mapper.createObjectNode();
            if (!inputContent.equals("")) {
                paramNode = mapper.readTree(inputContent);
            }

            //JsonNode returnEntry = mapper.readTree("{\"__returnCode\":{}}");
            JsonNode returnEntry = mapper.readTree(pcmlPath.getReturnEntries().toString());

            // JsonNode staticValues = mapper.readTree("{\"e\":10}");
            JsonNode staticValues = pcmlPath.getStaticValues();

            ObjectNode newMetadata = (ObjectNode)  paramNode;
            newMetadata.setAll((ObjectNode) staticValues);
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

        sendMessage(jsonString);

        String message = receiveMessage();

        return message;
    }
}
