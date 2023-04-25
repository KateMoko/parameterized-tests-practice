package com.katemoko;

import com.codeborne.selenide.CollectionCondition;
import com.katemoko.data.Locale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class KoronaOnlineTransferPageTest extends TestBase {

    static Stream<Arguments> onlineTransfersLocaleTestDataProvider() {
        return Stream.of(
                Arguments.of(
                        Locale.EN, List.of("Send cash", "Send to China", "Deposit to my card", "FAQ", "Log in")
                ),
                Arguments.of(
                        Locale.RU, List.of("Отправить наличные", "Отправить в Китай", "Получить на карту", "Вопросы и ответы", "Войти")
                )
        );
    }

    @MethodSource("onlineTransfersLocaleTestDataProvider")
    @ParameterizedTest(name = "Для локали: {0} отображаются пункты меню: {1}")
    void onlineTransfersLocaleTest(Locale siteLocale, List<String> menuButtons) {
        open("https://koronapay.com/transfers/online/");
        $(String.format("#clickable-button-locale-%s", siteLocale.toString().toLowerCase())).click();
        $$(".no-mobile-nav a[tabindex='0']").shouldHave(CollectionCondition.texts(menuButtons));
    }
}