package com.ibm.resources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by joe on 9/7/16.
 */
@WebServlet(
        name = "ReadUserServlet",
        urlPatterns = {"/readuser"}
)
public class ReadUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userURL = req.getParameter("prefix") + req.getParameter("userurl");

        String url = req.getRequestURL().toString();

        String baseURL = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/proxy";

        String nextJSP = req.getContextPath() + "/proxy" + userURL.substring(baseURL.length() , userURL.length());



        System.out.println("Forward it to " + nextJSP);
        resp.sendRedirect(nextJSP);
//
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
//
//        dispatcher.include(req, resp);

    }
}

