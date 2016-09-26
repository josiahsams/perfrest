package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Forward;
import com.ibm.aix.ForwardPath;
import com.ibm.aix.Helpers;
import com.ibm.aix.PCML;
import io.swagger.util.Json;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 9/23/16.
 */
@WebServlet(
        name = "MainServlet",
        urlPatterns = {"/main"}
)
public class MainServlet  extends HttpServlet{
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

        ObjectMapper obj = Json.mapper();

        Forward fobj = Helpers.getForwards(this.getServletContext());

        List<ForwardPath> routeList = new ArrayList<ForwardPath>();

        for (ForwardPath path: fobj.getPaths()) {
            routeList.add(path);
        }

        String jsonContent = Helpers.getJsonContent(this.getServletContext());
        if(!jsonContent.equals("")) {
            req.setAttribute("jsonContent", jsonContent);
        }
        String nextJSP = "/main.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("routeList", routeList);
        if (fobj != null)
            req.setAttribute("paths", fobj);

        req.setAttribute("obj", obj);

        List<String> listLibraries = Helpers.listPCMLFiles(this.getServletContext());
        if (listLibraries != null) {
            req.setAttribute("listLibraries", listLibraries);
        }

        Cookie[] reqCookies = req.getCookies();
        for(Cookie c : reqCookies){
            if(c.getName().equals("library")) {
                String library = c.getValue();
                //System.out.println("Cookie value is " + library);
                PCML pObj = Helpers.loadPCML(library, this.getServletContext());
                if (pObj != null)
                    req.setAttribute("pcml", pObj);

            }
        }

        String tabAct = req.getParameter("tabActive");
        if(tabAct != null) {
            req.setAttribute("tabactive", tabAct);
        } else {
            req.setAttribute("tabactive", "listPath");
        }

        dispatcher.forward(req, resp);
    }


}
