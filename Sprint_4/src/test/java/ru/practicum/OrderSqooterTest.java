package ru.practicum;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.practicum.pages.MainPage;
import ru.practicum.pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderSqooterTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String subwayStation;
    private final String phoneNumber;
    private final By orderButton;

    @Rule
    public DriverFactory factory = new DriverFactory();

    public OrderSqooterTest(String firstName, String lastName, String address, String subwayStation, String phoneNumber, By orderButton) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.subwayStation = subwayStation;
        this.phoneNumber = phoneNumber;
        this.orderButton = orderButton;
    }

    @Parameterized.Parameters(name = "Тестовые данные: имя - {0}, фамилия - {1}, адрес - {2}, станция метро - {3}, номер телефона - {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Марк", "Тютчев", "кутузовсквя, 4", "чистые пруды", "89655434576", MainPage.orderButtonInHeader},
                {"Мия", "Теодор", "вознесенская, 64", "черкизовская", "89665434072", MainPage.orderButtonInHeader},
                {"Марк", "Тютчев", "кутузовсквя, 4", "чистые пруды", "89655434576", MainPage.orderButtonInMiddle},
                {"Мия", "Теодор", "вознесенская, 64", "черкизовская", "89665434072", MainPage.orderButtonInMiddle}
        });
    }

    @Test
    public void verifyOrderSuccessMessage() {
        WebDriver driver = factory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.acceptCookiesIfPresent();
        mainPage.clickOrderButton(orderButton);
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillOrderForm(firstName, lastName, address, subwayStation, phoneNumber);
        orderPage.clickNextButton();
        orderPage.orderSqooter();
    }




}
