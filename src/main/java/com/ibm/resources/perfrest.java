package com.ibm.resources;

/**
 * Created by joe on 24/7/16.
 */
import io.swagger.annotations.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.ibm.aix.*;

@Path("/perf")
@Api(value = "/perf",  description = "AIX Performance APIs",  tags = "perf")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class perfrest {
    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)  
    @ApiOperation(value = "Print System Configuration",
            notes = "Returns a System Configuration - test"
    )
    public LparConfig getConfig() {
	return perfstat.getLparConfig();
    }
}
