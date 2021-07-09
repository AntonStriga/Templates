package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestMethodsTests {

    private final String BASE_URI = "https://reqres.in/api";
    // private final String BASE_URL = "http://localhost:3000/";

    // Simple case without given()

    @Test (groups = {"GET"})
    public void test_01(){
        int expectedStatusCode = 200;
        Response response = get("https://reqres.in/api/users?page=2");

        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode,expectedStatusCode);
    }

    // GET request

    @Test (groups = {"GET"})
    public void test_02() {
        baseURI = BASE_URI;

        given()
            .get("/users?page=2")
        .then()
            .statusCode(200)
            .body("data[1].id",equalTo(8))
            .body("data[4].first_name", equalTo("George"))
            .body("data.first_name", hasItems("George","Rachel"))
        .log().all();
    }

    // POST request

    @Test (groups = {"POST"})
    public void test_03() {
        baseURI = BASE_URI;

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Anton");
        requestBody.put("job", "QA");

        given()
            .header("Content-Type","application/json")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .post("/users")
        .then()
            .statusCode(201)
        .log().all();
    }

    // PUT request

    @Test (groups = {"PUT"})
    public void test_04() {
        baseURI = BASE_URI;

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Anton");
        requestBody.put("job", "QA");

        given()
            .header("Content-Type","application/json")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .put("/users/2")
        .then()
            .statusCode(200)
        .log().all();
    }

    // PATCH request

    @Test (groups = {"PATCH"})
    public void test_05() {
        baseURI = BASE_URI;

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Anton");
        requestBody.put("job", "QA");

        given()
            .header("Content-Type","application/json")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(requestBody.toJSONString())
        .when()
            .patch("/users/2")
        .then()
            .statusCode(200)
        .log().all();
    }

    // DELETE request

    @Test (groups = {"DELETE"})
    public void test_06() {
        baseURI = BASE_URI;

        given()
        .when()
            .delete("/users/2")
        .then()
            .statusCode(204)
        .log().all();
    }

    // Schema validate example
    // schema reads from src/test/resources/usersSchema.json

    @Test (groups = {"GET"})
    public void test_07() {
        baseURI = BASE_URI;

        given()
            .get("/users?page=2")
        .then()
            .statusCode(200)
            .assertThat().body(matchesJsonSchemaInClasspath("usersSchema.json"))
        .log().all();
    }

}
