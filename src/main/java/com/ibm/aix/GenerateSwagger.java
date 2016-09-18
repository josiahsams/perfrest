package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.introspector.Property;
import io.swagger.converter.ModelConverters;
import io.swagger.models.*;
import io.swagger.models.Response;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.parameters.*;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import io.swagger.util.Json;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.*;

/**
 * Created by joe on 9/12/16.
 */
@WebServlet(
        name = "GenerateSwagger",
        urlPatterns = {"/swagger"}
)
public class GenerateSwagger extends HttpServlet {
    private final ObjectMapper obj = Json.mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //ObjectMapper obj = new ObjectMapper();
        //ObjectMapper objOrig = new ObjectMapper();
//
//        obj.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
//        obj.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


//        ParameterDeserializer deserializer =
//                new ParameterDeserializer();
//        deserializer.registerParameter("in", PathParameter.class);
//        deserializer.registerParameter("query", QueryParameter.class);
//
//        SimpleModule module =
//                new SimpleModule("PolymorphicAnimalDeserializerModule",
//                        new Version(1, 0, 0, null));
//        module.addDeserializer(Parameter.class, deserializer);
//
//
//        obj.registerModule(module);


        String defn = "{\"lpar\":{\"type\":\"object\",\"properties\":{\"hostname\":{\"type\":\"string\"}, \"processorFamily\":{\"type\":\"string\"},\"drives\":{\"type\":\"integer\",\"format\":\"int64\"},\"lcpus\":{\"type\":\"integer\",\"format\":\"int32\"},\"osbuild\":{\"type\":\"string\"}}}}";
        String tagsDefn = "{\"tags\":[{\"name\":\"perf\",\"description\":\"APIs to get Performance Metrics\"},{\"name\":\"config\",\"description\":\"APIs to get Configuration Metrics\"}]}";

        List<ForwardPath> routeList = new ArrayList<ForwardPath>();
        Forward fobj = null;

        try {

            InputStream is = getServletContext().getResourceAsStream("/WEB-INF/forward.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            fobj = obj.readValue(jsonTxt.toString(), Forward.class);

            for (ForwardPath path: fobj.getPaths()) {
                String pathstr = path.getRoute();
                routeList.add(path);
               // System.out.println(pathstr);

            }
            is.close();

        } catch (Exception e ) {
            System.err.println(e);
        }

        final Info info = new Info()
                .version("1.0.0")
                .title("Swagger Petstore");

        final Contact contact = new Contact()
                .name("Swagger API Team")
                .email("foo@bar.baz")
                .url("http://swagger.io");

        info.setContact(contact);

        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "value");
        // info.setVendorExtension("x-test2", map);
        // info.setVendorExtension("x-test", "value");


//        List<String> produces = new ArrayList<String>();
//        produces.add("application/json");
//        produces.add("application/xml");
//        produces.add("text/xml");
//        produces.add("text/html");

        Tag tag1 = new Tag().name("perf").description("API to retrieve Perf Metrics");

        final Swagger swagger = new Swagger().basePath("/proxy")
                .info(info)
                .host("petstore.swagger.io")
//                .securityDefinition("api-key", new ApiKeyAuthDefinition("key", In.HEADER))
                .scheme(Scheme.HTTP).tag(tag1)
                .consumes("application/json"); // .model("lpar", lparModel)
                //.produces(produces);



//        final Operation post = new Operation()
//                .summary("adds a new pet")
//                .description("you can add a new pet this way")
//                .tag("Pet Operations")
//                .operationId("add     pet")
//                .defaultResponse(errorResponse)
//                .parameter(new BodyParameter()
//                        .description("the pet to add")
//                        .schema(new RefModel().asDefault("Person")));
//
//        swagger.path("/pets", new Path().get(get).post(post));

        Map<String, Path> pathMap = new HashMap<String, Path>();

        for (ForwardPath path: fobj.getPaths()) {

            if (path.getSwaggerInfo() == null || path.getSwaggerInfo().getOperations().isEmpty()) continue;

            Map<HttpMethod, Operation> operations = path.getSwaggerInfo().getOperationMap();
            Path pathObj = new Path();

            for (Map.Entry<HttpMethod, Operation> operationMapEntry : operations.entrySet()) {

                HttpMethod httpMethod = operationMapEntry.getKey();
                Operation operation = operationMapEntry.getValue();

                List<String> produces = operation.getProduces();
                String summary = operation.getSummary();
                String description = operation.getDescription();
                List<String> tags = operation.getTags();
                String operationId = operation.getOperationId();

                StringBuilder tagList = new StringBuilder();
                for(String tag : tags) {
                    if (tagList.length() != 0) {
                        tagList.append(",");
                    }
                    tagList.append(tag);
                }

                final Operation op = new Operation()
                        .produces(produces)
                        .summary(summary)
                        .description(description)
                        .tag(tagList.toString())
                        .operationId(operationId);

//                op.parameter(new QueryParameter()
//                        .name("tags")
//                        .description("tags to filter by")
//                        .required(false)
//                        .property(new StringProperty())
//                );

                List<Parameter> parameters = operation.getParameters();

                for (Parameter param : parameters) {

                    String inType = param.getIn();
                    String paramName = param.getName();
                    String paramDesc = param.getDescription();
                    Boolean paramReq = param.getRequired();


                    Parameter paramObj = null;

                    if ("query".equals(inType)) {
                        paramObj = new QueryParameter().name(paramName)
                                .description(paramDesc)
                                .required(paramReq)
                                // This app is made to handle only String Property
                                .property(new StringProperty());

                    } else if ("header".equals(inType)) {
                        // Not Implemented
                    } else if ("path".equals(inType)) {
                        paramObj = new PathParameter()
                                .name(paramName)
                                .description(paramDesc)
                                .property(new StringProperty());
                    } else if ("formData".equals(inType)) {
                        // Not Implemented
                    } else if ("body".equals(inType)) {
                        // Not Implemented
                    } else if ("cookie".equals(inType)) {
                        // Not Implemented
                    }

                    if (paramObj != null)
                        op.parameter(paramObj);

                }

//
//                final Response response = new Response()
//                        .description("pets returned")
//                        .schema(new RefProperty().asDefault("lpar"));
//                // .example("application/json", "fun!");
//
//                final Response errorResponse = new Response()
//                        .description("error response");
////                .schema(new RefProperty().asDefault("Error"));

                Map<String, Response> responseMaps = operation.getResponses();

                if (responseMaps != null) {
                    for (Map.Entry<String, Response> responseEntry : responseMaps.entrySet()) {
                        Response opResp = responseEntry.getValue();
                        if ("default".equals(responseEntry.getKey())) {
                            op.defaultResponse(opResp);
                            continue;
                        }
                        int key = Integer.parseInt(responseEntry.getKey());
                        op.response(key, opResp);
                    }
                }

                if (httpMethod == HttpMethod.GET) {
                    //System.out.println("GET Method "+ path.getRoute());
                    //pathMap.put(path.getRoute(), new Path().get(op));
                    pathObj.get(op);
 //                   swagger.path(path.getRoute(), new Path().get(op));
                } else if (httpMethod == HttpMethod.POST) {
                    //System.out.println("POST Method "+ path.getRoute());

                    pathObj.post(op);
                    //pathMap.put(path.getRoute(), new Path().get(op));
   //                 swagger.path(path.getRoute(), new Path().post(op));
                } else {
                    // Not Implemented for PUT, DELETE, UPDATE, PATCH, HEAD, OPTIONS
                }
            }
            swagger.path(path.getRoute(), pathObj);

//            if(!pathMap.isEmpty())
//                swagger.setPaths(pathMap);
//
        }

        // swagger.path("/pets/{petId}", new Path().get(get));
        final String swaggerJson = obj.writerWithDefaultPrettyPrinter().writeValueAsString(swagger);
//
//        String yamlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(swagger);
//
//        // ObjectNode objNode = (ObjectNode)mapper.readTree(swaggerJson);
//
        //ObjectNode defnObjNode = (ObjectNode)obj.readTree(defn);

        String defnFromJson = obj.writeValueAsString(fobj.getDefinitions());

        ObjectNode defnObjNode = (ObjectNode)obj.readTree(defnFromJson);

        ObjectNode swaggerObjNode = (ObjectNode)obj.readTree(swaggerJson);

        String tagsFromJson = "{\"tags\":"+obj.writeValueAsString(fobj.getTags())+"}";

        //ObjectNode tagsDefnObjNode = (ObjectNode)obj.readTree(tagsDefn);

        ObjectNode tagsDefnObjNode = (ObjectNode)obj.readTree(tagsFromJson);

        swaggerObjNode.set("definitions", defnObjNode);
        swaggerObjNode.set("tags", tagsDefnObjNode.get("tags"));

        //System.out.println(swaggerObjNode.toString());
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(swaggerObjNode.toString());
        out.flush();

//        String message = "<b>Swagger Definition: </b><br>" ;
//        message += swaggerJson;
//        req.setAttribute("infomessage", message);
//
//        String nextJSP = "/routes.jsp";
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
//        req.setAttribute("routeList", routeList);
//        dispatcher.forward(req, resp);

    }


    class ParameterDeserializer extends StdDeserializer<Parameter>
    {
        private Map<String, Class<? extends Parameter>> registry =
                new HashMap<String, Class<? extends Parameter>>();

        ParameterDeserializer()
        {
            super(Parameter.class);
        }

        void registerParameter(String uniqueAttribute,
                            Class<? extends Parameter> ParameterClass)
        {
            registry.put(uniqueAttribute, ParameterClass);
        }

        @Override
        public Parameter deserialize(
                JsonParser jp, DeserializationContext ctxt)
                throws IOException
        {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            ObjectNode root =  mapper.readTree(jp);
            Class<? extends Parameter> parameterClass = null;
            Iterator<Map.Entry<String, JsonNode>> elementsIterator =
                    root.fields(); //getFields();

            Parameter result = null;
            JsonNode inNode = root.get("in");

            if (inNode != null) {

                String in = inNode.asText();
                if ("query".equals(in)) {
                    result = Json.mapper().convertValue(root, QueryParameter.class);
                } else if ("header".equals(in)) {
                    result = Json.mapper().convertValue(root, HeaderParameter.class);
                } else if ("path".equals(in)) {

                    result = Json.mapper().convertValue(root, PathParameter.class);
                } else if ("formData".equals(in)) {
                    result = Json.mapper().convertValue(root, FormParameter.class);
                } else if ("body".equals(in)) {
                    result = Json.mapper().convertValue(root, BodyParameter.class);
                } else if ("cookie".equals(in)) {
                    result = Json.mapper().convertValue(root, CookieParameter.class);
                }
            }

            return result;

//            while (elementsIterator.hasNext())
//            {
//                Map.Entry<String, JsonNode> element=elementsIterator.next();
//                String name = element.getKey();
//                System.out.println("name :: " + name );
//                if (registry.containsKey(name))
//                {
//                    parameterClass = registry.get(name);
//                    System.out.println("Got it !!");
//                    break;
//                }
//            }
//            System.out.println(parameterClass);
//            if (parameterClass == null) return null;
//            return mapper.readValue(jp, parameterClass);
        }
    }

}

