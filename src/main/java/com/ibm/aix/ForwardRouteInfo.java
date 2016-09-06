package com.ibm.aix;

/**
 * Created by joe on 8/23/16.
 */
public class ForwardRouteInfo {
    String scheme;
    String host;
    String port;
    String method;
    String additionalQueryParams;
    String bodyMessage;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAdditionalQueryParams() {
        return additionalQueryParams;
    }

    public void setAdditionalQueryParams(String additionalQueryParams) {
        this.additionalQueryParams = additionalQueryParams;
    }
}
