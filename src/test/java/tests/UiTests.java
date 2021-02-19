package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("ui")
public class UiTests extends TestBaseUi {
    String url = config.ConfigHelper.getWebUrl();

    @Test
    @AllureId("1685")
    @Feature("Week Promo")
    @DisplayName("Проверяем количества элементов в блоке \"Предложения недели!\"")
    void checkWeekPromoBlockSize() {
        step("Открываем главную страницу", () -> {
            open(url);
        });
        step("Проверка равенства количества элементов 6 ", () -> {
            $(byText("Предложения недели!")).parent().sibling(0).
                    $$(byAttribute("style^", "width")).shouldHaveSize(6);
        });
    }


    @Test
    @AllureId("1686")
    @Feature("Search")
    @DisplayName("Проверка наличия среди результатов поиска процессора \"Процессор AMD Ryzen 5 2600 BOX\"")
    void searchTest() {
        step("Открываем главную страницу", () -> {
            open(url);
        });
        step("Ввести в поле поиска \"процессор\" ", () -> {
            $(byName("search")).val("процессор").pressEnter();
        });
        step("Проверить, что среди результатов есть \"Процессор AMD Ryzen 5 2600 BOX\" ", () -> {
            $(byAttribute("data-widget", "row"), 1).shouldHave(text("Процессор AMD Ryzen 5 2600 BOX"));
        });

    }

    @Test
    @AllureId("1687")
    @Feature("Search Filters")
    @DisplayName("Проверка выделения чекбоксов при поиске")
    void searchTestWithFilters() {
        SelenideElement searchFilters = $(byAttribute("data-widget", "searchResultsFilters"));

        step("Открыть ссылку с результатами поиска", () -> {
            open(url + "/category/ventilyatory-10724/?from_global=true&text=вентилятор");
        });
        step("Выбрать фильтр \"Homgeek\" ", () -> {
            searchFilters.$(byText("Homgeek")).click();
        });
        step("Проверить, что фильтр  \"Homgeek\" выбран", () -> {
            searchFilters.$(byText("Homgeek")).closest("label").$("input").shouldBe(checked);
        });
        step("Выбрать фильтр \"Настольная\" ", () -> {
            searchFilters.$(byText("розовый")).click();
        });
        step("Проверить, что фильтр  \"Настольная\" выбран", () -> {
            searchFilters.$(byText("розовый")).closest("label").$("input").shouldBe(checked);
        });
    }

    @Test
    @AllureId("1683")
    @Feature("Cart")
    @DisplayName("Проверка того, что товары успешно добавились в корзину")
    void addToCart() {
        String product1 = "Процессор AMD Ryzen 5 2600 BOX";
        String productUrl1 = url + "/context/detail/id/149949595/";
        String product2 = "Микроволновая печь Samsung ME-88SUG, 90000009888, серый";
        String productUrl2 = url + "/context/detail/id/149308749/";

        step("Открыть ссылку на первый товар", () -> {
            open(productUrl1);
        });
        step("Добавить первый товар в корзину", () -> {
            $("[data-widget='webAddToCart'] button").click();
            sleep(1000);
        });
        step("Открыть ссылку на второй товар", () -> {
            open(productUrl2);
        });
        step("Добавить второй товар в корзину", () -> {
            $("[data-widget='webAddToCart'] button").click();
            sleep(1000);
        });
        step("Перейти в корзину", () -> {
            $(byAttribute("data-widget", "cart")).click();
        });
        step("Проверить, что корзина содержит товар " + product1, () -> {
            $(byAttribute("data-widget", "row"), 2).shouldHave((text(product1)));
        });
        step("Проверить, что корзина содержит товар " + product2, () -> {
            $(byAttribute("data-widget", "row"), 2).shouldHave((text(product2)));
        });
    }

    @Test
    @AllureId("1684")
    @Feature("Location")
    @DisplayName("Проверка смены города")
    void changeCity() {
        step("Открыть главную страницу", () -> {
            open(url);
        });
        step("Кликнуть на город в левом верхнем углу", () -> {
            $(byAttribute("data-widget", "topBar")).$(("div>div"), 1).click();
        });
        step("Выбрать Новосибирск", () -> {
            $(byText("Новосибирск, Новосибирская область")).click();
        });
        step("Проверить, что в качестве города выбран Новосибирск", () -> {
            $(byAttribute("data-widget", "topBar")).shouldHave(text("Новосибирск"));
        });
    }

    @Test
    @Feature("Certificate")
    @DisplayName("Проверить изменение стоимости после выбора другого сертификата")
    void checkPriceCertificateAfterChange() {
        step("Открыть страницу с сертификатами", () -> {
            open(url+"/context/detail/id/135382627/");
        });
        step("Выбрать сертификат за 5000 руб.", () -> {
            $(byAttribute("data-widget", "webAspects")).
                    $(byText("5000")).closest("div").parent().click();
        });
        step("Проверить, что поменялась стоимость сертификата над кнопкой добавления в корзину", () -> {
            $(byAttribute("data-widget", "webPrice")).shouldHave(text("5 000"));
        });
    }

}


