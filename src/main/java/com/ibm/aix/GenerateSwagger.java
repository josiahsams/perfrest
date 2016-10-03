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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchpath =req.getParameter("searchpath");

        if (searchpath == null || searchpath.equals("")) {
            ObjectNode swaggerObjNode = Helpers.getSwaggerObject(req.getContextPath(), req.getServletContext());
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(swaggerObjNode.toString());
            out.flush();
        } else  {
            ObjectNode swaggerObjNode = Helpers.getSwaggerObjectWithName(req.getContextPath(),
                    req.getServletContext(), searchpath);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(swaggerObjNode.toString());
            out.flush();
        }
    }
}

