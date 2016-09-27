package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.util.Json;
import org.apache.commons.io.filefilter.WildcardFileFilter;

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
                return node.toString();
//                node = node.get("data");
//                if (node != null) {
//                    node = node.get("LparConfig");
//                    if (node != null) {
//                        message = node.toString();
//
//                    } else {
//                        System.out.println("LparConfig is missing");
//                    }
//                } else {
//                    System.out.println("Data is missing");
//                }
            } else {
                System.out.println("Got No Response");
            }
        } catch (IOException e) {
            System.out.println("Connection Error with Data Provider");
            e.printStackTrace();
        }
        return message;
    }

    public static String getJsonContent(String fileName, ServletContext context) {
        StringBuilder jsonTxt = new StringBuilder();
        try {
//            System.out.println(context.getRealPath("/WEB-INF/forward.json"));
//            SwaggerDeserializationResult swaggerDeser = new SwaggerParser().readWithInfo("http://petstore.swagger.io/v2/swagger.json", null, true);
//            System.out.println(Json.pretty(swaggerDeser.getSwagger()));

            InputStream is = context.getResourceAsStream(fileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            is.close();

        } catch (Exception e ) {
            System.err.println(e);
            return "";
        }
        return jsonTxt.toString();
    }

    public static Forward getForwards(ServletContext context) {
        Forward fobj = null;

        try {
            ObjectMapper obj = Json.mapper();
            fobj = obj.readValue(getJsonContent("/WEB-INF/forward-expanded.json", context), Forward.class);
        } catch (Exception e ) {
            System.err.println(e);
            return null;
        }

        return fobj;
    }

    public static List<String> listPCMLFiles(ServletContext context) {
        List<String> fileList = new ArrayList<String>();

        for (String ot : context.getResourcePaths("/WEB-INF/pcml/")) {
            if (!ot.contains("expanded")) {
                String[] splitPath = ot.split("/");
                int len = splitPath.length;
                String pathWithExt = splitPath[len - 1];
                fileList.add(pathWithExt.substring(0, pathWithExt.length() - 5));
            }
        }
        return fileList;
    }

    public static PCML loadPCML(String libraryName, ServletContext context) {
        ObjectMapper obj = Json.mapper();

        PCML pobj = null;
        try {
            InputStream is = context.getResourceAsStream("/WEB-INF/pcml/"+libraryName+"-expanded.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            pobj = obj.readValue(jsonTxt.toString(), PCML.class);

            is.close();
        } catch (Exception e ) {
            System.err.println(e);
        }
        return pobj;
    }

}
