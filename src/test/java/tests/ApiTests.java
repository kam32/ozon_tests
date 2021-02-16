package tests;

import io.qameta.allure.AllureId;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spec.Spec;

import static config.ConfigHelper.getWebUrl;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static utils.FileUtils.readStringFromFile;

@Tag("api")
public class ApiTests {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = getWebUrl();
    }

    @Test
    @AllureId("1680")
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLogin() {
        String body = readStringFromFile("src/test/resources/json/unsuccessfulLogin.json");
        String cookie = readStringFromFile("src/test/resources/cookies/unsuccessfulLogin.txt");

        Spec.request()
                .body(body)
                .cookie(cookie)
                .when()
                .post("/api/composer-api.bx/_action/emailOtpEntry")
                .then()
                .statusCode(200)
                .log().body()
                .body("error", is("Пользователь с указанным email не найден"))
                .body("data.accessToken", is(""))
        ;
    }

    @Test
    @AllureId("1681")
    @DisplayName("Добавление товара в корзину")
    void addToCart() {
        String body = "[{\"id\":139907347,\"quantity\":1}]";

        String cookie = readStringFromFile("src/test/resources/cookies/addToCart.txt");

        Spec.request()
                .body(body)
                .cookie(cookie)
                .when()
                .post("/api/composer-api.bx/_action/addToCart")
                .then()
                .statusCode(200)
                .log().body()
                .body("success", is(true))
                .body("cart.cartItems.id", hasItem(139907347))
                .body("cart.cartItems.qty", hasItem(1));
        ;
    }

    @Test
    @AllureId("1682")
    @DisplayName("Изменение города")
    void changeCity() {
        String body = readStringFromFile("src/test/resources/json/changeCity.json");
        String cookie = readStringFromFile("src/test/resources/cookies/changeCity.txt");

        Spec.request()
                .cookie(cookie)
                .body(body)
                .when()
                .post("/api/location/v2/user/location")
                .then()
                .log().body()
                .statusCode(200)
                .body("areaId", is(22671))
                .body("city", is("Челябинск"));
    }
}
