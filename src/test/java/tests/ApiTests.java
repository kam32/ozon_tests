package tests;

import io.qameta.allure.AllureId;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spec.Spec;

import static config.ConfigHelper.getWebUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static spec.Spec.request;

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

        request()
                .body(body)
                .when()
                .post("https://seller.ozon.ru/api/site/user/login")
                .then()
                .statusCode(400)
                .log().body()
                .body("error.code", is("FAIL_LOGIN"));
    }

    @AllureId("1694")
    @Test
    @DisplayName("Проверка содержания в категории 89268908  товара\"Пленка самоклеящаяся канцелярская\"")
    void checkCategoryName() {
        Spec.request()
                .header("Client-Id", "836")
                .header("Api-Key", "0296d4f2-70a1-4c09-b507-904fd05567b9")
                .when()
                .get("https://cb-api.ozonru.me/v1/categories/tree/89268908")
                .then()
                .log().body()
                .statusCode(200)
                .body("result.category_id", hasItem(89268908))
                .body("result.title", hasItem("Пленка самоклеящаяся канцелярская"));
    }
}
