package helpers;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    public Map<String, String> getCookies() {
        return given()
                .header("User-Agent", "PostmanRuntime/7.26.10")
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .when()
                .get("https://www.ozon.ru")
                .then()
                .log().all()
                .statusCode(200)
                .extract().cookies();
    }
}
