package com.ibm.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.aix.Forward;
import com.ibm.aix.ForwardPath;
import com.ibm.aix.Helpers;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joe on 9/7/16.
 */
@WebServlet(
        name = "ProxyServlet",
        urlPatterns = {"/proxy/*"}
)
public class ProxyServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = req.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/";

        // extra 7 space is to skip '/proxy/'
        String subResources = url.substring(url.length() - req.getRequestURI().length() + req.getContextPath().length() +7 , url.length());


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

            List<ForwardPath> routeList = Helpers.getRouteList(this.getServletContext());

            String nextJSP = "/routes.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            req.setAttribute("routeList", routeList);
            if (fobj != null)
                req.setAttribute("paths", fobj);

            req.setAttribute("obj", obj);
            dispatcher.include(req, resp);
            return;
        }

        String pattern = selectedPath.getRoute(); // "/path/{param1}/path2/{param2}";

        String getcontentTemplate = selectedPath.getRouteInfo().getAdditionalQueryParams();
        //"?name=sdfdsf&param1={param1}&param2={param2}";

        int patternCount = StringUtils.countMatches(pattern, "/");

        HashMap<Integer, String> patternMap = new HashMap<Integer, String>();

        String[] patternSplit = StringUtils.split(pattern, "/");

        System.out.println(subResources);
        int index = 0;
        for (String str : patternSplit) {
            if (StringUtils.contains(str, "{")) {
                int length = str.length();
                String key = str.substring(0, length - 1).substring(1, length - 1);
                patternMap.put(index, key);
            }
            index++;
        }
        int count = StringUtils.countMatches(subResources, "/");
        count++;
        // As path doesn't start with "/" like new1/sdf/sdf2/sfdrf/ksdffd/ , add 1 to the count
        while (StringUtils.endsWith(subResources, "/")) {
            subResources = subResources.substring(0, subResources.length() - 1);
            count--;
        }

        String[] inputParams = StringUtils.split(subResources, "/");
        HashMap<String, String> userinput = new HashMap<String, String>();

        String queryData = "";
        String pathData = selectedPath.getRouteInfo().getPath();

        if (count == patternCount) {
            System.out.println("count is " + count);
            Iterator itr = patternMap.keySet().iterator();
            while (itr.hasNext()) {
                Integer key = (Integer) itr.next();
                userinput.put(patternMap.get(key), inputParams[key]);
            }

            itr = userinput.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                System.out.println("Key is " + key + " Value is " + userinput.get(key));
            }


            Matcher m = p.matcher(getcontentTemplate);
            queryData = getcontentTemplate;

            while (m.find()) {
                //System.out.println(m.group());
                String val1 = m.group().replace("{", "").replace("}", "");
                //System.out.println(val1);
                queryData = (queryData.replace(m.group(), userinput.get(val1)));
            }

            queryData = queryData.replaceAll("&", "&amp;");
            System.out.println(queryData);

            pathData = selectedPath.getRouteInfo().getPath();
            Matcher pathMatcher = p.matcher(pathData);


            while (pathMatcher.find()) {
                //System.out.println(m.group());
                String val1 = pathMatcher.group().replace("{", "").replace("}", "");
                // System.out.println(val1);
                pathData = (pathData.replace(pathMatcher.group(), userinput.get(val1)));

            }
            System.out.println(pathData);

        } else
            System.out.println("Not Matched");


        StringBuilder newQueryParams = new StringBuilder();

        boolean delimflag = false;
        if (queryData != "") {
            newQueryParams.append("?" + queryData);
            delimflag = true;
        }

        String queryParams = req.getQueryString();

        if (queryParams != null) {
            if (delimflag) {
                newQueryParams.append("&amp;");

            } else {
                newQueryParams.append("?");
                delimflag = true;
            }

            newQueryParams.append(queryParams);

        }

//        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//        Iterator itr = queryParams.keySet().iterator();
//
//        while (itr.hasNext()) {
//            if (delimflag) {
//                newQueryParams.append("&amp;");
//
//            } else {
//                newQueryParams.append("?");
//                delimflag = true;
//            }
//            String key = (String) itr.next();
//            newQueryParams.append(key);
//            String value = queryParams.getFirst(key);
//            newQueryParams.append("=" + value);
//        }

        //newQueryParams.toString().replaceAll("&", "&amp;");

        //String path = uriInfo.getAbsolutePathBuilder().path("..").build() + subResources + strbuilder;


        // String path = uriInfo.getBaseUriBuilder().path(perfrest.class).build().normalize().toString()

        String path = baseURL + "/" + subResources + newQueryParams;

        String newPath = selectedPath.getRouteInfo().getScheme() + "://" +
                selectedPath.getRouteInfo().getHost() + ":" + selectedPath.getRouteInfo().getPort() +
                pathData + newQueryParams;

        System.out.println(newPath);
        PrintWriter out = resp.getWriter();

        resp.setContentType("text/html");

        String message = "<b>Forwarding Request to </b>" + newPath ;
        req.setAttribute("message", message);

        List<ForwardPath> routeList = Helpers.getRouteList(this.getServletContext());

        String nextJSP = "/routes.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("routeList", routeList);
        if (fobj != null)
            req.setAttribute("paths", fobj);

        req.setAttribute("obj", obj);
        dispatcher.include(req, resp);

//        try {
//            URI uri = new URI(path);
//
//            //System.out.println(uri.toString());
//
//
//            out.println("<h3>Forwarding Request to </h3><pre>" + newPath + "</pre>");
//
////        return Response.ok("<h3>Forwarding Request to </h3><pre>" + newPath + "</pre>")
////                .header("Content-Type", "text/html;charset=UTF-8")
////                .build();
////        //return Response.ok(forwardGet(uri.toString())).build();
//        } catch (Exception e) {
//            out.println("Error has occurred ");
//        }
//
//        out.println("");
    }
}
