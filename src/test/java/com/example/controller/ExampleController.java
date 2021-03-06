package com.example.controller;

import com.example.dto.ExampleError;
import com.example.dto.ExampleRequest;
import com.example.dto.ExampleResponse;
import com.example.dto.PaginationExampleRequest;
import io.swagger.annotations.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@Api(tags = {"Example"})
public class ExampleController {

    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully created", response = ExampleResponse.class),
        @ApiResponse(code = 400, message = "Something went wrong with creation", response = ExampleError.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", required = true, dataTypeClass = ExampleRequest.class)
    })
    @ApiOperation(value = "CreateExample", nickname = "CreateExample")
    public HttpResponse<Void> create(Long id) {
        return null;
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully found", response = ExampleResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ExampleError.class)
    })
    @ApiOperation(value = "GetExample", nickname = "GetExample")
    public HttpResponse<Void> get(Long id) {
        return null;
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully found", response = ExampleResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ExampleError.class)
    })
    @ApiOperation(value = "GetNestedExample", nickname = "GetNestedExample")
    public HttpResponse<Void> getNested(Long id, UUID otherId) {
        return null;
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully found", response = ExampleResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ExampleError.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "page", dataTypeClass = Integer.class),
        @ApiImplicitParam(paramType = "query", name = "pageSize", dataTypeClass = Integer.class),
        @ApiImplicitParam(paramType = "query", name = "order", dataTypeClass = String.class),
        @ApiImplicitParam(paramType = "query", name = "sort", dataTypeClass = String.class)
    })
    @ApiOperation(value = "GetPaginatedExample", nickname = "GetNestedExample")
    public HttpResponse<Void> getPaginated(@ApiParam(hidden = true) PaginationExampleRequest paginatedRequest) {
        return null;
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully found", response = ExampleResponse[].class),
        @ApiResponse(code = 404, message = "Not Found", response = ExampleError.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", required = true, dataTypeClass = ExampleRequest[].class)
    })
    @ApiOperation("put")
    public HttpResponse<Void> put() {
        return null;
    }
}
