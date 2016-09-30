package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Forward;
import com.ibm.aix.ForwardPath;
import io.swagger.util.Json;
import jdk.nashorn.internal.runtime.GlobalConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Created by joe on 9/30/16.
 */
@WebServlet(
        name = "ProxyServlet",
        urlPatterns = {"/proxy/*"}
)
public class ProxyServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        forwardRequest("GET", req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        forwardRequest("POST", req, resp);
    }

    private void forwardRequest(String method, HttpServletRequest req, HttpServletResponse resp) {

        String Rurl = req.getRequestURL().toString();
        String baseURL = Rurl.substring(0, Rurl.length() - req.getRequestURI().length()) + req.getContextPath() + "/";

        // extra 7 space is to skip '/proxy/'
       // String subResources = Rurl.substring(Rurl.length() - req.getRequestURI().length() + req.getContextPath().length() +7 , Rurl.length());
        String subResources = Rurl.substring(Rurl.length() - req.getRequestURI().length() + req.getContextPath().length() + 1, Rurl.length());


        System.out.println("Base URI " + baseURL);
        System.out.println("path : " + subResources);

        Pattern p = Pattern.compile("(\\{\\w+\\})");
        ForwardPath selectedPath = new ForwardPath();
        ObjectMapper obj = Json.mapper();
        Forward fobj = null;
        try {

            InputStream is = getServletContext().getResourceAsStream("/WEB-INF/forward.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while ((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            //ObjectMapper obj = new ObjectMapper();

            fobj = obj.readValue(jsonTxt.toString(), Forward.class);


            for (ForwardPath path : fobj.getPaths()) {
                String pathstr = path.getRoute();
                pathstr = pathstr.substring(1, pathstr.length());
                String pathPattern = p.matcher(pathstr).replaceAll("(.*)");

                System.out.println(pathPattern + " <=> " + subResources);

                if (subResources.matches(pathPattern)) {
                    System.out.println(path.getRoute() + " has Match !! ");
                    selectedPath = path;
                }
            }
            is.close();

        } catch (Exception e) {
            System.err.println(e);
        }

        // Handle Invalid Path.
        if (selectedPath.getRoute() == null) {
            String message = "<b>URL pattern is not configured </b>";
            req.setAttribute("errormessage", message);

//            List<ForwardPath> routeList = Helpers.getRouteList(this.getServletContext());

            String nextJSP = "/main";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
//            req.setAttribute("routeList", routeList);
//            if (fobj != null)
//                req.setAttribute("paths", fobj);
//
//            req.setAttribute("obj", obj);
            try {
                dispatcher.forward(req, resp);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }
            return;
        }





        final boolean hasoutbody = (method.equals("POST"));

        try {
            final URL url = new URL("http://localhost:4000/newpath"
                    // + req.getRequestURI()
                    + (req.getQueryString() != null ? "?" + req.getQueryString() : ""));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);

            final Enumeration<String> headers = req.getHeaderNames();
            while (headers.hasMoreElements()) {
                final String header = headers.nextElement();
                final Enumeration<String> values = req.getHeaders(header);
                while (values.hasMoreElements()) {
                    final String value = values.nextElement();
                    conn.addRequestProperty(header, value);
                }
            }

            //conn.setFollowRedirects(false);  // throws AccessDenied exception
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(hasoutbody);
            conn.connect();

            final byte[] buffer = new byte[16384];
            while (hasoutbody) {
                final int read = req.getInputStream().read(buffer);
                if (read <= 0) break;
                conn.getOutputStream().write(buffer, 0, read);
            }

            resp.setStatus(conn.getResponseCode());
            for (int i = 0; ; ++i) {
                final String header = conn.getHeaderFieldKey(i);
                if (header == null) break;
                final String value = conn.getHeaderField(i);
                resp.setHeader(header, value);
            }

            while (true) {
                final int read = conn.getInputStream().read(buffer);
                if (read <= 0) break;
                resp.getOutputStream().write(buffer, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // pass
        }
    }

}
