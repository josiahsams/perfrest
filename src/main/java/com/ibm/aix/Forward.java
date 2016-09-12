package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by joe on 8/23/16.
 */

public class Forward {
    ForwardPath [] paths;
    Object definitions;

    public Object getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Object definitions) {
        this.definitions = definitions;
    }

    public ForwardPath[] getPaths() {
        return paths;
    }

    public void setPath(ForwardPath[] paths) {
        this.paths = paths;
    }
}

