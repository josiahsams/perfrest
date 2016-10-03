package com.ibm.aix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.models.*;
import io.swagger.models.parameters.*;
import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.util.Json;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 8/13/16.
 */
public class Helpers {
    static Socket socket;
    static DataOutputStream os;
    static DataInputStream is;

    public static DatagramSocketInfo connectUDP(String hostname, int port) {
        DatagramSocketInfo dInfo = null;

        try {
            dInfo = new DatagramSocketInfo(new DatagramSocket(), InetAddress.getByName(hostname),
                        port);
        } catch (Exception e) {
            System.out.println("Connection Error: Data Provider Server unknown");
            e.printStackTrace();
        }
        return dInfo;
    }

    public static boolean sendMessageUDP(DatagramSocketInfo dInfo, String message) {
        boolean result = true;
        try {
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                    dInfo.getIpaddress(), dInfo.getPort());
            dInfo.getDsocket().send(sendPacket);
        } catch (Exception e) {
            result = false;
            System.out.println("Error Sending to Data Provider");
            e.printStackTrace();
        }
        return result;
    }

    public static String receiveMessageUDP(DatagramSocketInfo dInfo){

        ObjectMapper mapper = Json.mapper();
        byte[] receiveData = new byte[10240];

        String message = "{}";
        try {

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            dInfo.getDsocket().receive(receivePacket);
            String str = new String(receivePacket.getData()).trim();

            System.out.println("Received Data : " + str);

            if (!str.equals("")) {
                JsonNode nodes = mapper.readValue(str, JsonNode.class);

                JsonNode node = nodes.get("response");
                if (node != null) {
                    return node.toString();
                } else {
                    System.out.println("Got No Response");
                }
            }
        } catch (IOException e) {
            System.out.println("Error Receiving from Data Provider");
            e.printStackTrace();
        }
        return message;
    }

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

    public static PCMLForward getPCMLForwards(ServletContext context) {
        PCMLForward fobj = null;

        try {
            ObjectMapper obj = Json.mapper();
            fobj = obj.readValue(getJsonContent("/WEB-INF/pcmlforward.json", context), PCMLForward.class);
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

    public static ObjectNode getSwaggerObject(String basePath, ServletContext context) {

        ObjectMapper obj = Json.mapper();
        Forward fobj = getForwards(context);
        PCMLForward pcmlForward = Helpers.getPCMLForwards(context);

        final Info info = new Info()
                .version("1.0.0")
                .title("Performance APIs");

        final Contact contact = new Contact()
                .name("IBM AIX Performance Tools")
                .email("aix@ibm.com")
                .url("http://wwww.ibm.com");

        info.setContact(contact);

        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "value");

        final Swagger swagger = new Swagger().basePath(basePath)
                .info(info);


        for (Map.Entry<String, PCMLPath> pcmlPathMap : pcmlForward.getPaths().entrySet())
        {
            swagger.path(pcmlPathMap.getKey(), pcmlPathMap.getValue().getSwaggerInfo());
        }


        for (ForwardPath path: fobj.getPaths()) {

            if (path.getSwaggerInfo() == null || path.getSwaggerInfo().getOperations().isEmpty()) continue;
            swagger.path(path.getRoute(), path.getSwaggerInfo());
        }

        try {
            String swaggerJson = obj.writerWithDefaultPrettyPrinter().writeValueAsString(swagger);

            String defnFromJson = obj.writeValueAsString(fobj.getDefinitions());

            ObjectNode defnObjNode = (ObjectNode) obj.readTree(defnFromJson);

            ObjectNode swaggerObjNode = (ObjectNode) obj.readTree(swaggerJson);

            String tagsFromJson = "{\"tags\":" + obj.writeValueAsString(fobj.getTags()) + "}";

            ObjectNode tagsDefnObjNode = (ObjectNode) obj.readTree(tagsFromJson);
            swaggerObjNode.set("definitions", defnObjNode);
            swaggerObjNode.set("tags", tagsDefnObjNode.get("tags"));

            return swaggerObjNode;

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        // Return an empty ObjectNode on error
        return obj.createObjectNode();

    }

}
