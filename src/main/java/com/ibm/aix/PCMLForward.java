package com.ibm.aix;

import io.swagger.models.Tag;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 9/27/16.
 */
public class PCMLForward {
    Map<String, PCMLPath> paths;
    Object definitions;
    List<Tag> tags;

    public Map<String, PCMLPath> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, PCMLPath> paths) {
        this.paths = paths;
    }

    public Object getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Object definitions) {
        this.definitions = definitions;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
