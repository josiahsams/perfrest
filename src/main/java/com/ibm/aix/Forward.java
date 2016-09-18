package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.models.Tag;

import java.util.List;

/**
 * Created by joe on 8/23/16.
 */

public class Forward {
    ForwardPath [] paths;
    Object definitions;
    List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

