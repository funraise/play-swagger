package io.funraise.swagger;

import com.example.dto.ExampleResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SwaggerFactoryTest {

    @Test
    public void testResponse() {
        var factory = new SwaggerFactory();

        var response = factory.response(ExampleResponse.class, "An example response");

        assertEquals(
            "#/definitions/ExampleResponse",
            response.getResponseSchema().getReference()
        );
    }
}
