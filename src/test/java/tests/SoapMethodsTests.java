package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SoapMethodsTests {

    private final String BASE_URI = "http://www.dneonline.com";
    private final String XML_FILE_PATH = "src/test/resources/soapFile.xml";
    private final String XSD_FILE_PATH = "soapSchema.xsd";
    private final String CHARSET_NAME = "UTF-8";

    @Test
    public void test_01() throws IOException {
        baseURI = BASE_URI;

        File file = new File(XML_FILE_PATH);
        if(file.exists()) {
            System.out.println(">> File Exists");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        String requestBody = IOUtils.toString(fileInputStream,CHARSET_NAME);

        given()
            .contentType("text/xml")
            .accept(ContentType.XML)
            .body(requestBody)
        .when()
            .post("/calculator.asmx")
        .then()
            .statusCode(200)
        .log().all()
        .and()
            .body("//*:AddResult.text()",equalTo("5"));
    }

    // With XSD schema validation

    @Test
    public void test_02() throws IOException {
        baseURI = BASE_URI;

        File file = new File(XML_FILE_PATH);
        if(file.exists()) {
            System.out.println(">> File Exists");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        String requestBody = IOUtils.toString(fileInputStream,CHARSET_NAME);

        given()
            .contentType("text/xml")
            .accept(ContentType.XML)
            .body(requestBody)
        .when()
            .post("/calculator.asmx")
        .then()
            .statusCode(200)
        .log().all()
        .and()
            .assertThat().body(matchesXsdInClasspath(XSD_FILE_PATH));
    }
}
