package tests;

import dataProviders.DataProviders;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DataDrivenTests extends DataProviders {

    private final String BASE_URI = "http://localhost:3000/";

    // Without excel file

    @Test(groups = {"POST"}, dataProvider = "DataForPost")
    public void test_01(String firstName, String lastName, int subjectId) {
        baseURI = BASE_URI;

        JSONObject requestBody = new JSONObject();

        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        requestBody.put("subjectId", subjectId);

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

    // Without excel file

    @Test (groups = {"DELETE"}, dataProvider = "DataForDelete")
    public void test_02(int userToDeleteId) {
        baseURI = BASE_URI;

        given()
        .when()
            .delete("/users/" + userToDeleteId)
        .then()
            .statusCode(200)
        .log().all();
    }

    // With data from excel file

    @Test(groups = {"POST"}, dataProvider = "DataFromExcel")
    public void test_03(String firstName, String lastName, double subjectId) {
        baseURI = BASE_URI;

        JSONObject requestBody = new JSONObject();

        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        requestBody.put("subjectId", subjectId);

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
}
