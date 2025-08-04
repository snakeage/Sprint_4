package ru.practicum;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.pages.MainPage;
import ru.practicum.pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

import static ru.practicum.util.TestData.ORDER_DATA;

@RunWith(Parameterized.class)
public class OrderScooterTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String subwayStation;
    private final String phoneNumber;
    private final boolean useHeaderButton;

    @Rule
    public DriverFactory factory = new DriverFactory();

    public OrderScooterTest(String firstName, String lastName, String address, String subwayStation, String phoneNumber, boolean useHeaderButton) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.subwayStation = subwayStation;
        this.phoneNumber = phoneNumber;
        this.useHeaderButton = useHeaderButton;
    }

    @Parameterized.Parameters(name = "Тестовые данные: имя - {0}, фамилия - {1}, адрес - {2}, станция метро - {3}, номер телефона - {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(ORDER_DATA);
    }

    @Test
    public void testVerifyOrderSuccessMessage() {
        WebDriver driver = factory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.acceptCookiesIfPresent();

        // Выбираем кнопку заказа по флагу из тестовых данных
        if (useHeaderButton) {
            mainPage.clickOrderButtonInHeader();
        } else {
            mainPage.clickOrderButtonInMiddle();
        }
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillOrderForm(firstName, lastName, address, subwayStation, phoneNumber);
        orderPage.clickNextButton();
        orderPage.orderScooter();
    }

}
