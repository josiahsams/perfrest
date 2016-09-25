package com.ibm.aix;

/**
 * Created by joe on 9/23/16.
 */
public class PCMLInfo {

    String id;
    String version;
    String library;
    String oslevel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getOslevel() {
        return oslevel;
    }

    public void setOslevel(String oslevel) {
        this.oslevel = oslevel;
    }
}
