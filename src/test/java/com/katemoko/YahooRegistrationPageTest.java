package com.katemoko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class YahooRegistrationPageTest extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://login.yahoo.com/account/create?lang=ru-RU");
    }

    @CsvFileSource(resources = "/yahooEmailValidation.csv", delimiter = '|')
    @ParameterizedTest(name = "Для введеного имени пользователя: {0} отображается сообщение об ошибке: {1}")
    @DisplayName("Проверка сообщения об ошибке при вводе невалидного имени пользователя")
    @Tags({
            @Tag("WEB"),
            @Tag("NORMAL")
    })
    void userNameValidationErrorMessageTest(String userName, String expectedErrorMessage) {
        $("#usernamereg-userId").setValue(userName).pressTab();
        $("#reg-error-userId").shouldHave(text(expectedErrorMessage));
    }


    @CsvSource(value = {
            "tttt | Ваш пароль недостаточно надежен. Попробуйте увеличить число символов",
            "rrrrrrrrrr | Ваш пароль небезопасен. Используйте более надежный пароль",
            "qwerty1234 | Вы выбрали слишком простой пароль. Придумайте более надежный пароль"
    },
            delimiter = '|')
    @ParameterizedTest(name = "Для введеного пароля: {0} отображается сообщение об ошибке: {1}")
    @DisplayName("Проверка сообщения об ошибке при вводе невалидного пароля")
    @Tags({
            @Tag("WEB"),
            @Tag("NORMAL")
    })
    void passwordValidationErrorMessageTest(String password, String expectedErrorMessage) {
        $("#usernamereg-password").setValue(password).pressTab();
        $("#reg-error-password").shouldHave(text(expectedErrorMessage));
    }

    @ValueSource(strings = {
            "$0meStrongValue",
            "simplervalue"
    })
    @ParameterizedTest(name = "Для введеного пароля: {0} не отображается сообщение об ошибке")
    @DisplayName("Проверка, что сообщение об ошибке не отображается при вводе валидного пароля")
    @Tags({
            @Tag("WEB"),
            @Tag("HIGH")
    })
    void validPasswordTest(String password) {
        $("#usernamereg-password").setValue(password).pressTab();
        $("#reg-error-password").shouldNotBe(visible);
    }
}