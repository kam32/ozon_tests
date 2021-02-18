package tests;

import helpers.ApiHelper;
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
    void unsuccessfulLoginSeller() {
        String body = "{\"user_name\":\"fafcac@afjpafj.com\",\"password\":\"fafac\"}";

        Spec.request()
                .body(body)
                .when()
                .post("https://seller.ozon.ru/api/site/user/login")
                .then()
                .statusCode(400)
                .log().body()
                .body("error.code", is("FAIL_LOGIN"));
    }

    @Test
    @AllureId("1681")
    @DisplayName("Добавление товара в корзину")
    void addToCart() {
        String body = "[{\"id\":139907347,\"quantity\":1}]";

        Spec.request()
                .body(body)
                .cookies(new ApiHelper().getCookies())
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

        Spec.request()
                .body(body)
                .cookies(new ApiHelper().getCookies())
                .when()
                .post("/api/location/v2/user/location")
                .then()
                .log().body()
                .statusCode(200)
                .body("areaId", is(22671))
                .body("city", is("Челябинск"));
    }
}
