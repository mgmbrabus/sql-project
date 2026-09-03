package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        // SQLHelper.cleanDatabase();
        com.codeborne.selenide.Selenide.closeWebDriver();
    }


    @Test
        void shouldSuccessfullyLogin() {

        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();

        var verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCode();

        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
        void shouldBlockAfterThreeWrongPasswords() {
        var loginPage = new LoginPage();
        var authInfo = new DataHelper.AuthInfo("vasya", "wrong_password");
        loginPage.invalidLogin(authInfo);
        loginPage.invalidLogin(authInfo);
        loginPage.invalidLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();

    }
}