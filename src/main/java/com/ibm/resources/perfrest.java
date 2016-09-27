package com.ibm.resources;

/**
 * Created by joe on 24/7/16.
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.jersey.api.view.Viewable;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.ibm.aix.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/perf")
@Api(value = "/perf",  description = "AIX Performance APIs",  tags = "perf")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class perfrest {
    @POST
    @Path("/config")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Print System Configuration",
            notes = "Returns a System Configuration - sample test data"
    )
    public LparConfig getConfig(InputFormat input) {

        UriBuilder ub = UriBuilder.fromUri("http://localhost:3000/")
                //.path("{a}")
                .queryParam("querystring", input);

        URI userUri = ub.
                build();


        Response.temporaryRedirect(userUri);

        return LparConfig.getLparConfig(input);
    }


    @POST
    @Path("/configpost1/{subResources: .*}")

    public Response getpost(@PathParam("subResources") String subResources, String input, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        StringBuilder strbuilder = new StringBuilder();

        Iterator itr = queryParams.keySet().iterator();

        boolean flag = false;
        while(itr.hasNext()) {
            if (flag)
            {
                strbuilder.append("&");

            } else {
                strbuilder.append("?");
                flag = true;
            }
            String key = (String)itr.next();
            strbuilder.append(key);
            String value = queryParams.getFirst(key);
            strbuilder.append("="+value);
        }


        //String path = uriInfo.getAbsolutePathBuilder().path("..").build() + subResources + strbuilder;
        String path = uriInfo.getBaseUriBuilder().path(perfrest.class).build().normalize().toString()
                + "/" + subResources  + strbuilder;
        try {
            URI uri = new URI(path);

            System.out.println(uri.toString());

            //return Response.temporaryRedirect(uri).build();
            return Response.ok(forwardPost(uri.toString(), input)).build();
        }catch (Exception e) {
            Response.ok().build();
        }
        return Response.ok().build();
    }
    @POST
    @Path("/configpost/{subResources: .*}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getpost(@PathParam("subResources") String subResources, InputFormat input, @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        StringBuilder strbuilder = new StringBuilder();

        Iterator itr = queryParams.keySet().iterator();

        boolean flag = false;
        while(itr.hasNext()) {
            if (flag)
            {
                strbuilder.append("&");

            } else {
                strbuilder.append("?");
                flag = true;
            }
            String key = (String)itr.next();
            strbuilder.append(key);
            String value = queryParams.getFirst(key);
            strbuilder.append("="+value);
        }


        //String path = uriInfo.getAbsolutePathBuilder().path("..").build() + subResources + strbuilder;
        String path = uriInfo.getBaseUriBuilder().path(perfrest.class).build().normalize().toString()
                + "/" + subResources  + strbuilder;
        try {
            URI uri = new URI(path);

            System.out.println(uri.toString());

            ObjectMapper mapper = Json.mapper();
            String body = mapper.writeValueAsString(input);

            //return Response.temporaryRedirect(uri).build();
            return Response.ok(forwardPost(uri.toString(), body)).build();
        }catch (Exception e) {
            Response.ok().build();
        }
        return Response.ok().build();
    }

    @Context ServletContext servletContext;


    @GET
    @Path("/config2/{subResources: .*}")
    @Produces( MediaType.TEXT_PLAIN +"; charset=UTF-8")
    public Response get(@PathParam("subResources") String subResources, @Context UriInfo uriInfo) {

        Pattern p = Pattern.compile("(\\{\\w+\\})");
        ForwardPath selectedPath = new ForwardPath();
        try {

            InputStream is = servletContext.getResourceAsStream("/WEB-INF/forward.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonTxt = new StringBuilder();

            String line = "";
            while((line = r.readLine()) != null) {
                jsonTxt.append(line);
            }

            ObjectMapper obj = Json.mapper();
            Forward fobj = obj.readValue(jsonTxt.toString(), Forward.class);


            for (ForwardPath path: fobj.getPaths()) {
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

        } catch (Exception e ) {
            System.err.println(e);
        }
        String pattern =   selectedPath.getRoute(); // "/path/{param1}/path2/{param2}";

        String postcontentTemplate = "{'name':'sdfdsf','param1': '{param1}', 'param2': '{param2}' }";
        String getcontentTemplate = selectedPath.getRouteInfo().getAdditionalQueryParams();
        //"?name=sdfdsf&param1={param1}&param2={param2}";

        int patternCount = StringUtils.countMatches(pattern,"/");

        HashMap<Integer, String> patternMap = new HashMap<Integer, String>();

        String[] patternSplit = StringUtils.split(pattern, "/");

        System.out.println(subResources);
        int index = 0;
        for(String str : patternSplit) {
            if (StringUtils.contains(str, "{")) {
                int length = str.length();
                String key = str.substring(0, length-1).substring(1,length-1);
                patternMap.put(index,key);
            }
            index++;
        }
        int count = StringUtils.countMatches(subResources, "/");
        count++;
        // As path doesn't start with "/" like new1/sdf/sdf2/sfdrf/ksdffd/ , add 1 to the count
        while (StringUtils.endsWith(subResources, "/")) {
            subResources = subResources.substring(0, subResources.length()-1);
            count--;
        }

        String [] inputParams = StringUtils.split(subResources, "/");
        HashMap<String, String> userinput = new HashMap<String, String>();

        String queryData = "";
        String pathData = selectedPath.getRouteInfo().getPath();

        if(count == patternCount) {
            System.out.println("count is " + count);
            Iterator itr = patternMap.keySet().iterator();
            while(itr.hasNext()){
                Integer key = (Integer)itr.next();
                userinput.put(patternMap.get(key), inputParams[key]);
            }

            itr = userinput.keySet().iterator();
            while(itr.hasNext()){
                String key = (String)itr.next();
                System.out.println("Key is "+ key + " Value is " + userinput.get(key));
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

        }
        else
            System.out.println("Not Matched");

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        StringBuilder newQueryParams = new StringBuilder();

        Iterator itr = queryParams.keySet().iterator();

        boolean delimflag = false;
        if (queryData != "") {
            newQueryParams.append("?" + queryData);
            delimflag = true;
        }
        while(itr.hasNext()) {
            if (delimflag)
            {
                newQueryParams.append("&amp;");

            } else {
                newQueryParams.append("?");
                delimflag = true;
            }
            String key = (String)itr.next();
            newQueryParams.append(key);
            String value = queryParams.getFirst(key);
            newQueryParams.append("="+value);
        }

        //newQueryParams.toString().replaceAll("&", "&amp;");

        //String path = uriInfo.getAbsolutePathBuilder().path("..").build() + subResources + strbuilder;
        String path = uriInfo.getBaseUriBuilder().path(perfrest.class).build().normalize().toString()
                + "/" + subResources  + newQueryParams;

        String newPath = selectedPath.getRouteInfo().getScheme() + "://" +
                selectedPath.getRouteInfo().getHost() + ":" + selectedPath.getRouteInfo().getPort() +
                pathData + newQueryParams;

        System.out.println(newPath);
        try {
            URI uri = new URI(path);

            //System.out.println(uri.toString());

            return Response.ok("<h3>Forwarding Request to </h3><pre>" + newPath + "</pre>")
                    .header("Content-Type", "text/html;charset=UTF-8")
                    .build();
            //return Response.ok(forwardGet(uri.toString())).build();
        }catch (Exception e) {
            Response.ok().build();
        }
        return Response.ok().build();
    }


    String forwardGet(String path) {
        try {
            Client client = Client.create();

            WebResource webResource = client.resource(path);

            // POST method
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            // check response status code
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }


            // display response
              return response.getEntity(String.class);
//            System.out.println("Output from Server .... ");
//            System.out.println(output + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    String forwardPost(String path, String input) {
        try {
            Client client = Client.create();

            WebResource webResource = client.resource(path);

            input = input.replace("\n","");
            System.out.println(input);
            // POST method
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json")
                    .post(ClientResponse.class, input);

            // check response status code
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }


            // display response
            return response.getEntity(String.class);
//            System.out.println("Output from Server .... ");
//            System.out.println(output + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Path("/new1")
    public String new1(@QueryParam("param1") String param1, @Context UriInfo uriInfo) {

        StringBuilder path = new StringBuilder();
        try {
            path.append(uriInfo.getBaseUriBuilder());
            String basePath = uriInfo.getBaseUri().getPath();

            System.out.println(path);


        } catch(Exception e) {
            System.err.println(e);
        }
        return param1+path;
    }

    @POST
    @Path("/new2")
    @Consumes(MediaType.APPLICATION_JSON)

    @ApiOperation(value = "Print System Configuration",
            notes = "Returns a System Configuration - sample test data"
    )
    public InputFormat new2(InputFormat input) {
        StringBuilder sb = new StringBuilder();

        System.out.println("invoked new2");
        try {
            ObjectMapper mapper = new ObjectMapper();
            sb.append(mapper.writeValueAsString(input));
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("{}");
        }
        System.out.println(sb);
        return input;
    }

    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Path("/config1")
    @Consumes(MediaType.APPLICATION_JSON)

    @ApiOperation(value = "Print System Configuration",
            notes = "Returns a System Configuration - sample test data"
    )
    public LparConfig getConfig1(InputFormat input) {

        UriBuilder ub = UriBuilder.fromUri("http://localhost:3000/")
                //.path("{a}")
                .queryParam("querystring", input);


        URI userUri = ub.
                build();

        System.out.println(ub);

        System.out.println(userUri);
       // return Response.temporaryRedirect(userUri);

        StringBuilder sb = new StringBuilder();


        try {

            ObjectMapper mapper = new ObjectMapper();
            sb.append(mapper.writeValueAsString(input));

            //InputStream is = httpRequest.getInputStream();

//            java.util.Scanner scanner = new java.util.Scanner(httpRequest.getInputStream());
//
//
//            //BufferedInputStream bis = new BufferedInputStream(is);
//            // BufferedReader reader = new BufferedReader(is.getReader());
//            String line;
//            byte[] buf = new byte[100];
//
//            while (scanner.hasNextLine()) {
//                sb.append(scanner.nextLine());
//            }
            //reader.close();
            // bis.close();
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("{}");
        }
        System.out.println(sb);

        try {

            String mt = httpRequest.getContentType();
            Enumeration accept = httpRequest.getHeaders("accept");
            System.out.println(httpRequest.getHeaders("accept").nextElement());
            System.out.println(mt);
            Client client = Client.create();
            WebResource webResource2 = client.resource("http://localhost:3000");
            ClientResponse response2 = webResource2.accept(MediaType.TEXT_PLAIN_TYPE).type(mt).header("accept", accept)
                    .post(ClientResponse.class, sb.toString());
            if (response2.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
            }

            String output2 = response2.getEntity(String.class);
            System.out.println("\n============getFtoCResponse============");
            System.out.println(output2);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LparConfig();
    }




}
