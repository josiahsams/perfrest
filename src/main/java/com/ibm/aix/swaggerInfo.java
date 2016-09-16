package com.ibm.aix;

import io.swagger.models.Model;
import io.swagger.models.Response;
import io.swagger.models.Scheme;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 9/12/16.
 */
public class swaggerInfo {
    protected List<Tag> tags;
    protected List<Scheme> schemes;
    protected List<String> consumes;
    protected List<String> produces;
    protected List<Parameter> parameters;
    protected Map<String, Response> responses;



}
