package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.util.Json;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 8/13/16.
 */
public class Helpers {
    static Socket socket;
    static DataOutputStream os;
    static DataInputStream is;

    public static boolean connect(String hostname, int port) {
        boolean result = true;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            result = false;
            System.out.println("Connection Error: Data Provider Server unknown");
            e.printStackTrace();
        }
        return result;
    }

    public static boolean sendMessage(String message) {
        boolean result = true;
        try {

            PrintWriter pw = new PrintWriter(os);
            pw.println(message);
            pw.flush();
            // os.close();
        } catch (Exception e) {
            result = false;
            System.out.println("Connection Error with Data Provider");
            e.printStackTrace();
        }
        return result;
    }

    public static String receiveMessage(){

        ObjectMapper mapper = Json.mapper();
        String message = "{}";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            JsonNode nodes = mapper.readValue(in, JsonNode.class);

            JsonNode node = nodes.get("response");
            if (node != null) {
                node = node.get("data");
                if (node != null) {
                    node = node.get("LparConfig");
                    if (node != null) {
                        message = node.toString();

                    } else {
                        System.out.println("LparConfig is missing");
                    }
                } else {
                    System.out.println("Data is missing");
                }
            } else {
                System.out.println("Got No Response");
            }
        } catch (IOException e) {
            System.out.println("Connection Error with Data Provider");
            e.printStackTrace();
        }
        return message;
    }


    public static List<ForwardPath> getRouteList(ServletContext context) {
        List<ForwardPath> routeList = new ArrayList<ForwardPath>();
        try {

            InputStream is = context.getResourceAsStream("/WEB-INF/forward.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            ObjectMapper obj = Json.mapper();
            Forward fobj = obj.readValue(jsonTxt.toString(), Forward.class);


            for (ForwardPath path: fobj.getPaths()) {
                String pathstr = path.getRoute();
                routeList.add(path);
                // System.out.println(pathstr);

            }
            is.close();

        } catch (Exception e ) {
            System.err.println(e);
        }

        for(ForwardPath route : routeList) {
            System.out.println(route.getRouteInfo().getHost());
        }
        return routeList;
    }
}
