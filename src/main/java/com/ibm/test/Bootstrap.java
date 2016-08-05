package com.ibm.test;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.*;

import io.swagger.models.auth.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Bootstrap extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        Info info = new Info()
                .title("Test Program")
                .description("This is a sample server ")
                .version("v1.0");


        ServletContext context = config.getServletContext();
        Swagger swagger = new Swagger().info(info);


        swagger.scheme(Scheme.HTTP);
        //swagger.setSchemes(new List<Scheme>[]{Scheme.HTTP})
        swagger.host("oak14.aus.stglabs.ibm.com:9080");
        swagger.basePath("/samplerest/rest");
        swagger.tag(new Tag()
                .name("perf")
                .description("Short Description about the project")
                );
        new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
    }
}
