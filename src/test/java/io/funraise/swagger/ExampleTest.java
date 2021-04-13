package io.funraise.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.funraise.swagger.play.RouteFactory;
import io.swagger.models.Info;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExampleTest {

    private static List<String> routesFile;
    private static String expectedJson;
    private static JsonMapper mapper;

    @BeforeClass
    public static void before() {
       routesFile = routesFile();
       expectedJson = expectedJson();
       mapper = JsonMapper.builder()
           .serializationInclusion(JsonInclude.Include.NON_NULL)
           .enable(SerializationFeature.INDENT_OUTPUT)
           .build();
    }

    @Test
    public void testExample() throws JsonProcessingException {
        var routeFactory = new RouteFactory();
        List<Route> routes = new ArrayList<>();
        for (var line : routesFile) {
            var components = line.split(" {4}");
            var route = routeFactory.create(components[0].trim(), components[1].trim(), components[2].trim());
            routes.add(route);
        }

        var swaggerFactory = new SwaggerFactory();
        var swagger = swaggerFactory.create(
            routes,
            info(),
            null,
            null
        );

        var json = mapper.valueToTree(swagger);
        var expected = mapper.reader().readTree(expectedJson);

        assertEquals(
            expected,
            json
        );
    }

    private Info info() {
        var info = new Info();
        info.setVersion("2.0");
        info.setTitle("Example");
        return info;
    }

    private static List<String> routesFile() {
        var resource = ExampleTest.class.getResource("/routes");
        if (resource == null) {
            throw new RuntimeException("Example routes file is missing");
        }
        try {
            var path = Paths.get(resource.toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read example routes file", e);
        }
    }

    private static String expectedJson() {
        var resource = ExampleTest.class.getResource("/swagger.json");
        if (resource == null) {
            throw new RuntimeException("Example swagger file is missing");
        }
        try {
            var path = Paths.get(resource.toURI());
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read example swagger file", e);
        }
    }
}
