package com.ibm.resources;

/**
 * Created by joe on 24/7/16.
 */
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.ibm.aix.*;

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
        return LparConfig.getLparConfig(input);
    }
}
