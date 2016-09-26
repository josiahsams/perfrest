package com.ibm.aix;

import io.swagger.models.Model;

import java.util.Map;

/**
 * Created by joe on 9/23/16.
 */
public class Funcs {
    Model returnCode;
    Map<String, Model> params ;

//    public CObject getReturnCode() {
//        return returnCode;
//    }
//
//    public void setReturnCode(CObject returnCode) {
//        this.returnCode = returnCode;
//    }
//
//    public Map<String, CObject> getParams() {
//        return params;
//    }
//
//    public void setParams(Map<String, CObject> params) {
//        this.params = params;
//    }


    public Model getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Model returnCode) {
        this.returnCode = returnCode;
    }

    public Map<String, Model> getParams() {
        return params;
    }

    public void setParams(Map<String, Model> params) {
        this.params = params;
    }
}
