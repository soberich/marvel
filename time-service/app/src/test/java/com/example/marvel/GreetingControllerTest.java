package com.example.marvel;

//import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

//@QuarkusTest
public class GreetingControllerTest {

//    @Test
    public void testGreeting() {
        given()
            .when().get("/greeting/world")
            .then()
                .statusCode(500)/*
                .body("message", is("HELLO WORLD!"))*/;
    }

}
