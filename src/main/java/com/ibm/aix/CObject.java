package com.ibm.aix;

/**
 * Created by joe on 9/23/16.
 */
public class CObject {
    String type;
    String $ref;
    CObject items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public CObject getItems() {
        return items;
    }

    public void setItems(CObject items) {
        this.items = items;
    }
}
