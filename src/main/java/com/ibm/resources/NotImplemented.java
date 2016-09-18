package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Forward;
import com.ibm.aix.ForwardPath;
import io.swagger.util.Json;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 9/18/16.
 */
@WebServlet(
        name = "NotImplemented",
        urlPatterns = {"/default"}
)
public class NotImplemented extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleRequest(req, resp);

    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ForwardPath> routeList = new ArrayList<ForwardPath>();
        ObjectMapper obj = Json.mapper();
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

//        for(ForwardPath route : routeList) {
//            System.out.println(route.getRouteInfo().getHost());
//        }

        String nextJSP = "/routes.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("routeList", routeList);
        if (fobj != null)
            req.setAttribute("paths", fobj);

        String message = "<b>Mock Operation Performed. " +
                "Complete Implementation is pending ...</b>";
        req.setAttribute("message", message);

        req.setAttribute("obj", obj);
        dispatcher.forward(req, resp);
    }
}
