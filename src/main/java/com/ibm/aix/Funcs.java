package com.ibm.aix;

import java.util.Map;

/**
 * Created by joe on 9/23/16.
 */
public class Funcs {
    CObject returnCode;
    Map<String, CObject> params ;

    public CObject getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(CObject returnCode) {
        this.returnCode = returnCode;
    }

    public Map<String, CObject> getParams() {
        return params;
    }

    public void setParams(Map<String, CObject> params) {
        this.params = params;
    }
}
