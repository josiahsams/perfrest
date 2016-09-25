package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Forward;
import com.ibm.aix.ForwardPath;

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
import com.ibm.aix.Helpers;
import io.swagger.util.Json;

/**
 * Created by joe on 9/5/16.
 */
@WebServlet(
        name = "RouteServlet",
        urlPatterns = {"/routes"}
)
public class RouteServlet  extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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


    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("add")) {

            addRoute(req, resp);

        }

    }

    private void addRoute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathName = req.getParameter("path");

        System.out.println("New Route is " + pathName);

        // Employee employee = new Employee(name, lastName, birthday, role, department, email);
        // long idEmployee = employeeService.addEmployee(employee);

        String message = "The new route (" + pathName + ") has been successfully created.";
        req.setAttribute("message", message);

//        List<ForwardPath> routeList = Helpers.getRouteList(this.getServletContext());

        String nextJSP = "/main";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
//        req.setAttribute("routeList", routeList);
        dispatcher.forward(req, resp);
    }



}

