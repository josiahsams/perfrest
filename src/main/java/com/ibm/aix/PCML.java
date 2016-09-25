package com.ibm.aix;

import io.swagger.models.Model;

import java.util.Map;

/**
 * Created by joe on 9/23/16.
 */
public class PCML {
    PCMLInfo pcml;
    Map<String, Model> definitions;
    Map<String, Funcs> functions;

    public PCMLInfo getPcml() {
        return pcml;
    }

    public void setPcml(PCMLInfo pcml) {
        this.pcml = pcml;
    }

    public Map<String, Model> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, Model> definitions) {
        this.definitions = definitions;
    }

    public Map<String, Funcs> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, Funcs> functions) {
        this.functions = functions;
    }
}
