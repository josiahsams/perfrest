package com.ibm.aix;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by joe on 10/3/16.
 */
public class DatagramSocketInfo {
    private DatagramSocket dsocket;
    private InetAddress ipaddress;
    private int port;

    public DatagramSocketInfo(DatagramSocket dsocket, InetAddress ipaddress, int port) {
        this.dsocket = dsocket;
        this.ipaddress = ipaddress;
        this.port = port;
    }

    public DatagramSocket getDsocket() {
        return dsocket;
    }

    public void setDsocket(DatagramSocket dsocket) {
        this.dsocket = dsocket;
    }

    public InetAddress getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(InetAddress ipaddress) {
        this.ipaddress = ipaddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
