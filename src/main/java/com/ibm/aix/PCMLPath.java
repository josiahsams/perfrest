package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.models.Path;

/**
 * Created by joe on 9/27/16.
 */
public class PCMLPath {
    PCMLHeaderInfo pcmlInfo;
    PCMLRoutingInfo routingInfo;
    Path swaggerInfo;
    JsonNode staticValues;
    JsonNode returnEntries;

    public PCMLHeaderInfo getPcmlInfo() {
        return pcmlInfo;
    }

    public void setPcmlInfo(PCMLHeaderInfo pcmlInfo) {
        this.pcmlInfo = pcmlInfo;
    }

    public PCMLRoutingInfo getRoutingInfo() {
        return routingInfo;
    }

    public void setRoutingInfo(PCMLRoutingInfo routingInfo) {
        this.routingInfo = routingInfo;
    }

    public Path getSwaggerInfo() {
        return swaggerInfo;
    }

    public void setSwaggerInfo(Path swaggerInfo) {
        this.swaggerInfo = swaggerInfo;
    }

    public JsonNode getStaticValues() {
        return staticValues;
    }

    public void setStaticValues(JsonNode staticValues) {
        this.staticValues = staticValues;
    }

    public JsonNode getReturnEntries() {
        return returnEntries;
    }

    public void setReturnEntries(JsonNode returnEntries) {
        this.returnEntries = returnEntries;
    }
}
