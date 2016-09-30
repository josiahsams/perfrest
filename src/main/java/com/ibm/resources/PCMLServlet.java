package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Helpers;
import com.ibm.aix.PCMLForward;
import com.ibm.aix.PCMLPath;
import com.ibm.aix.Routing;
import io.swagger.util.Json;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by joe on 9/27/16.
 */
@WebServlet(
        name = "PCMLServlet",
        urlPatterns = {"/cfunc/*"}
)
public class PCMLServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
        forwardRequest("GET", req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
        forwardRequest("POST", req, resp);
    }

    private void forwardRequest(String method, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String pathToLookup = "/cfunc/" + pathParts[pathParts.length-1] ;
        //System.out.println(pathToLookup);

        String userDefinedRoutes = Helpers.getJsonContent("/WEB-INF/pcmlforward.json", this.getServletContext());

        StringBuffer sb = new StringBuffer();
        if (method.equals("POST")) {

            String line;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }
        }

        ObjectMapper mapper = Json.mapper();

        PCMLForward pcmlForward = mapper.readValue(userDefinedRoutes, PCMLForward.class);

        PCMLPath pcmlPath = pcmlForward.getPaths().get(pathToLookup);
        PrintWriter out = resp.getWriter();
        if (pcmlPath == null) {
            out.println("Path ." + pathToLookup + " not configured");
            return;
        }

        String json = Routing.makeCFuncCall(pcmlPath, sb.toString());

        //System.out.println(json);

        resp.setContentType("application/json");

        out.print(json);
        out.flush();
    }
}
