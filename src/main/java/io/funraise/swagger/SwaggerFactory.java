package io.funraise.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.converter.ModelConverterContextImpl;
import io.swagger.jackson.ModelResolver;
import io.swagger.models.Info;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Response;
import io.swagger.models.SecurityRequirement;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SwaggerFactory {

    private final ModelConverterContextImpl converter;

    public SwaggerFactory() {
        converter = new ModelConverterContextImpl(new ModelResolver(Json.mapper()));
    }

    public Swagger create(
        List<Route> routes,
        Info info,
        Map<String, SecuritySchemeDefinition> securityDefinition,
        List<SecurityRequirement> securityRequirements
    ) {
        var swagger = new Swagger();
        var paths = routes
            .stream()
            .collect(Collectors.groupingBy(Route::path))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, entry -> path(entry.getValue())));

        swagger.setPaths(paths);
        swagger.setDefinitions(converter.getDefinedModels());
        swagger.setInfo(info);
        swagger.setSecurityDefinitions(securityDefinition);
        swagger.setSecurity(securityRequirements);

        return swagger;
    }

    private Path path(List<Route> routes) {
        var path = new Path();

        for (var route : routes) {
            switch (route.httpMethod()) {
                case "GET":
                    path.setGet(operation(route));
                    break;
                case "POST":
                    path.setPost(operation(route));
                    break;
                case "PUT":
                    path.setPut(operation(route));
                    break;
                case "DELETE":
                    path.setDelete(operation(route));
                    break;
            }
        }

        return path;
    }

    private Operation operation(Route route) {
        var operation = new Operation();

        var controllerMethod = route.controllerMethod();
        var method = controllerMethod.method();

        if (!method.isAnnotationPresent(ApiOperation.class)) {
            throw new RuntimeException("Cannot create swagger from routes without ApiOperation annotation: "+route);
        }
        var apiOperation = method.getAnnotation(ApiOperation.class);
        operation.setOperationId(apiOperation.value());
        for (var tag : apiOperation.tags()) {
            if (StringUtils.isNotBlank(tag)) {
                operation.addTag(tag);
            }
        }

        if (!method.getDeclaringClass().isAnnotationPresent(Api.class)) {
            throw new RuntimeException("Cannot create swagger from routes without Api annotation: "+route);
        }
        var api = method.getDeclaringClass().getAnnotation(Api.class);
        for (var tag : api.tags()) {
            if (StringUtils.isNotBlank(tag)) {
                operation.addTag(tag);
            }
        }

        operation.setParameters(parameters(route.path(), controllerMethod));

        if (method.isAnnotationPresent(ApiResponses.class)) {
            var apiResponses = method.getAnnotation(ApiResponses.class).value();
            for (var apiResponse : apiResponses) {
                operation.response(apiResponse.code(), response(apiResponse));
            }
        }

        return operation;
    }

    private List<Parameter> parameters(String path, ControllerMethod controllerMethod) {
        var method = controllerMethod.method();

        var list = new ArrayList<Parameter>();
        if (method.isAnnotationPresent(ApiImplicitParams.class)) {
            var implicitParams = Stream.of(method.getAnnotation(ApiImplicitParams.class).value())
                .map(this::parameter)
                .collect(Collectors.toList());

            list.addAll(implicitParams);
        }

        var pathParams = controllerMethod
            .parameters()
            .stream()
            .filter(parameter -> !parameter.equals("request"))
            .filter(parameter -> path.contains("{"+parameter+"}"))
            .map(this::pathParameter)
            .collect(Collectors.toList());

        var queryParams = controllerMethod.parameters()
            .stream()
            .filter(parameter -> !parameter.equals("request"))
            .filter(parameter -> !path.contains("{"+parameter+"}"))
            .map(this::queryParameter)
            .collect(Collectors.toList());

        list.addAll(pathParams);
        list.addAll(queryParams);

        return list;
    }

    private Parameter parameter(ApiImplicitParam apiImplicitParam) {
        switch (apiImplicitParam.paramType()) {
            case "path":
                return pathParameter(apiImplicitParam);
            case "body":
                return bodyParameter(apiImplicitParam);
            case "header":
                return headerParameter(apiImplicitParam);
            case "form":
                return formParameter(apiImplicitParam);
            default:
                return queryParameter(apiImplicitParam);
        }
    }

    private Parameter pathParameter(String parameter) {
        var result = new PathParameter();

        result.setName(parameter);
        result.required(true);

        return result;
    }

    private Parameter queryParameter(String parameter) {
        var result = new QueryParameter();

        result.setName(parameter);
        result.setRequired(false);

        return result;
    }

    private Parameter pathParameter(ApiImplicitParam apiImplicitParam) {
        var parameter = new PathParameter();

        parameter.setAccess(apiImplicitParam.access());
        parameter.setAllowEmptyValue(apiImplicitParam.allowEmptyValue());
        parameter.setCollectionFormat(apiImplicitParam.collectionFormat());
        parameter.setDefaultValue(apiImplicitParam.defaultValue());
        parameter.setName(apiImplicitParam.name());
        parameter.setDescription(apiImplicitParam.value());
        parameter.required(apiImplicitParam.required());

        return parameter;
    }

    private Parameter bodyParameter(ApiImplicitParam apiImplicitParam) {
        var parameter = new BodyParameter();

        parameter.setAccess(apiImplicitParam.access());
        parameter.setName(apiImplicitParam.name());
        parameter.setDescription(apiImplicitParam.value());
        parameter.setRequired(apiImplicitParam.required());
        parameter.setSchema(model(apiImplicitParam.dataTypeClass()));

        return parameter;
    }

    private Parameter headerParameter(ApiImplicitParam apiImplicitParam) {
        var parameter = new HeaderParameter();

        parameter.setAccess(apiImplicitParam.access());
        parameter.setAllowEmptyValue(apiImplicitParam.allowEmptyValue());
        parameter.setCollectionFormat(apiImplicitParam.collectionFormat());
        parameter.setDefaultValue(apiImplicitParam.defaultValue());
        parameter.setName(apiImplicitParam.name());
        parameter.setDescription(apiImplicitParam.value());
        parameter.required(apiImplicitParam.required());

        return parameter;
    }

    private Parameter formParameter(ApiImplicitParam apiImplicitParam) {
        var parameter = new FormParameter();

        parameter.setAccess(apiImplicitParam.access());
        parameter.setAllowEmptyValue(apiImplicitParam.allowEmptyValue());
        parameter.setCollectionFormat(apiImplicitParam.collectionFormat());
        parameter.setDefaultValue(apiImplicitParam.defaultValue());
        parameter.setName(apiImplicitParam.name());
        parameter.setDescription(apiImplicitParam.value());
        parameter.required(apiImplicitParam.required());

        return parameter;
    }

    private Parameter queryParameter(ApiImplicitParam apiImplicitParam) {
        var parameter = new QueryParameter();

        parameter.setAccess(apiImplicitParam.access());
        parameter.setAllowEmptyValue(apiImplicitParam.allowEmptyValue());
        parameter.setCollectionFormat(apiImplicitParam.collectionFormat());
        parameter.setDefaultValue(apiImplicitParam.defaultValue());
        parameter.setName(apiImplicitParam.name());
        parameter.setDescription(apiImplicitParam.value());
        parameter.required(apiImplicitParam.required());

        return parameter;
    }


    private Response response(ApiResponse apiResponse) {
        var response = new Response();

        response.setDescription(apiResponse.message());
        response.setResponseSchema(model(apiResponse.response()));

        return response;
    }

    public Response response(Class<?> responseType, String description) {
        Response response = new Response();
        response.setDescription(description);
        response.setResponseSchema(this.model(responseType));
        return response;
    }

    private Model model(Class<?> clazz) {
        var model = converter.resolve(clazz);
        if (model instanceof ModelImpl) {
            return new RefModel(((ModelImpl) model).getName());
        } else {
            return model;
        }
    }
}
