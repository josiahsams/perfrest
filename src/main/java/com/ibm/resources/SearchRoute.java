package com.ibm.resources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by joe on 10/3/16.
 */
@WebServlet(
        name = "SearchRoute",
        urlPatterns = {"/searchroute"}
)
public class SearchRoute extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleRequest(req, resp);

    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nextJSP = "/main";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);

        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        return;
    }


}
