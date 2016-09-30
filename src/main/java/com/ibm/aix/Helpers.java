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

        //List<ForwardPath> routeList = new ArrayList<ForwardPath>();
        ObjectMapper obj = Json.mapper();
        Forward fobj = getForwards(context);
        PCMLForward pcmlForward = Helpers.getPCMLForwards(context);

//        for (ForwardPath path: fobj.getPaths()) {
//            String pathstr = path.getRoute();
//            routeList.add(path);
//        }

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
//
//            Map<HttpMethod, Operation> operations = path.getSwaggerInfo().getOperationMap();
//            Path pathObj = new Path();
//
//            for (Map.Entry<HttpMethod, Operation> operationMapEntry : operations.entrySet()) {
//
//                HttpMethod httpMethod = operationMapEntry.getKey();
//                Operation operation = operationMapEntry.getValue();
//
//                List<String> produces = operation.getProduces();
//                String summary = operation.getSummary();
//                String description = operation.getDescription();
//                List<String> tags = operation.getTags();
//                String operationId = operation.getOperationId();
//
//                StringBuilder tagList = new StringBuilder();
//                for(String tag : tags) {
//                    if (tagList.length() != 0) {
//                        tagList.append(",");
//                    }
//                    tagList.append(tag);
//                }
//
//                final Operation op = new Operation()
//                        .produces(produces)
//                        .summary(summary)
//                        .description(description)
//                        .tag(tagList.toString())
//                        .operationId(operationId);
//
//                List<Parameter> parameters = operation.getParameters();
//
//                for (Parameter param : parameters) {
//
//                    String inType = param.getIn();
//
//                    Parameter paramObj = null;
//
//                    if ("query".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, QueryParameter.class);
//
//                    } else if ("header".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, HeaderParameter.class);
//                    } else if ("path".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, PathParameter.class);
//                    } else if ("formData".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, FormParameter.class);
//                    } else if ("body".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, BodyParameter.class);
//                    } else if ("cookie".equals(inType)) {
//                        paramObj = Json.mapper().convertValue(param, CookieParameter.class);
//                    }
//
//                    if (paramObj != null)
//                        op.parameter(paramObj);
//                }
//
//                Map<String, Response> responseMaps = operation.getResponses();
//
//                if (responseMaps != null) {
//                    for (Map.Entry<String, Response> responseEntry : responseMaps.entrySet()) {
//                        Response opResp = responseEntry.getValue();
//                        if ("default".equals(responseEntry.getKey())) {
//                            op.defaultResponse(opResp);
//                            continue;
//                        }
//                        int key = Integer.parseInt(responseEntry.getKey());
//                        op.response(key, opResp);
//                    }
//                }
//
//                if (httpMethod == HttpMethod.GET) {
//                    pathObj.get(op);
//                } else if (httpMethod == HttpMethod.POST) {
//                    pathObj.post(op);
//                } else {
//                    // Not Implemented for PUT, DELETE, UPDATE, PATCH, HEAD, OPTIONS
//                }
//            }
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
