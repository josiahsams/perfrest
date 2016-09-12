package com.ibm.aix;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.*;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 9/12/16.
 */
@WebServlet(
        name = "GenerateSwagger",
        urlPatterns = {"/swagger"}
)
public class GenerateSwagger extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper obj = new ObjectMapper();
        List<ForwardPath> routeList = new ArrayList<ForwardPath>();
        try {

            InputStream is = getServletContext().getResourceAsStream("/WEB-INF/forward.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }


            Forward fobj = obj.readValue(jsonTxt.toString(), Forward.class);


            for (ForwardPath path: fobj.getPaths()) {
                String pathstr = path.getRoute();
                routeList.add(path);
                System.out.println(pathstr);

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
        info.setVendorExtension("x-test2", map);
        info.setVendorExtension("x-test", "value");

        final Swagger swagger = new Swagger()
                .info(info)
                .host("petstore.swagger.io")
                .securityDefinition("api-key", new ApiKeyAuthDefinition("key", In.HEADER))
                .scheme(Scheme.HTTP)
                .consumes("application/json")
                .produces("application/json");


        final Operation get = new Operation()
                .produces("application/json")
                .summary("finds pets in the system")
                .description("a longer description")
                .tag("Pet Operations")
                .operationId("get pet by id")
                .deprecated(true);

        get.parameter(new QueryParameter()
                .name("tags")
                .description("tags to filter by")
                .required(false)
                .property(new StringProperty())
        );

        get.parameter(new PathParameter()
                .name("petId")
                .description("pet to fetch")
                .property(new LongProperty())
        );

        final Response response = new Response()
                .description("pets returned")
                .schema(new RefProperty().asDefault("Person"))
                .example("application/json", "fun!");

        final Response errorResponse = new Response()
                .description("error response")
                .schema(new RefProperty().asDefault("Error"));

        get.response(200, response)
                .defaultResponse(errorResponse);

        final Operation post = new Operation()
                .summary("adds a new pet")
                .description("you can add a new pet this way")
                .tag("Pet Operations")
                .operationId("add pet")
                .defaultResponse(errorResponse)
                .parameter(new BodyParameter()
                        .description("the pet to add")
                        .schema(new RefModel().asDefault("Person")));

        swagger.path("/pets", new Path().get(get).post(post));
        final String swaggerJson = obj.writeValueAsString(swagger);

        System.out.println(swaggerJson);
//        resp.setContentType("application/json");
//        PrintWriter out = resp.getWriter();
//        out.println(swaggerJson);
//        out.flush();

        String message = "<b>Swagger Definition: </b><br>" ;
        message += swaggerJson;
        req.setAttribute("infomessage", message);

        String nextJSP = "/routes.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("routeList", routeList);
        dispatcher.forward(req, resp);

    }
}

