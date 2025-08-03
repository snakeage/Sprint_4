package ru.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static ru.practicum.util.EnvConfig.EXPLICITY_TIMEOUT;

public class OrderPage {

    private final WebDriver driver;
    private final By firstName = By.cssSelector("input[placeholder='* Имя']");
    private final By lastName = By.cssSelector("input[placeholder='* Фамилия']");
    private final By address = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private final By subwayStation = By.cssSelector("input[placeholder='* Станция метро']");
    private final By subwaySuggestion = By.cssSelector(".select-search__select"); // если это дропдаун или поле автозаполнения
    private final By phoneNumber = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private final By dateInput = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private final By rentDurationDropdown = By.cssSelector(".Dropdown-placeholder");
    private final By firstRentOption = By.cssSelector("div.Dropdown-menu > .Dropdown-option:first-child");
    private final By blackCheckbox = By.cssSelector("input[type='checkbox'][id='black']");
    private final By comment = By.cssSelector("input[placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    private final By yesButtonToConfirmOrderScooter = By.xpath("//button[text()='Да']");
    private final By orderConfirmationModal = By.cssSelector(".Order_ModalHeader__3FDaJ");




    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickNextButton() {
        By nextButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Далее']");
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void setFirstName(String firstName) {
        driver.findElement(this.firstName).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(this.lastName).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(this.address).sendKeys(address);
    }

    public void setSubwayStation(String subwayStation) {
        driver.findElement(this.subwayStation).sendKeys(subwayStation);
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(subwaySuggestion));
        element.click();
    }

    public void setPhoneNumber(String phoneNumber) {
        driver.findElement(this.phoneNumber).sendKeys(phoneNumber);
    }

    public void fillOrderForm(String firstName, String lastName, String address, String subwayStation, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setSubwayStation(subwayStation);
        setPhoneNumber(phoneNumber);
    }


public void setTomorrowDate() {
    WebElement dateField = driver.findElement(dateInput);
    dateField.click();

    int tomorrowDay = LocalDate.now().plusDays(1).getDayOfMonth();

    String dayXpath = String.format(
            "//div[contains(@class,'react-datepicker__day') and text()='%d' and not(contains(@class,'react-datepicker__day--outside-month'))]",
            tomorrowDay);

    WebElement dayElement = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
            .until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath)));
    dayElement.click();
}


    public void selectRentDuration() {
        driver.findElement(rentDurationDropdown).click();
        driver.findElement(firstRentOption).click();

    }

    public void setColor() {
        driver.findElement(blackCheckbox).click();
    }

    public void setComment() {
        driver.findElement(comment).sendKeys("Нужен не битый.");
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void clickConfirmOrderButton() {
    driver.findElement(yesButtonToConfirmOrderScooter).click();
    }

    public boolean isOrderConfirmed() {
        try {
            WebElement modal = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal));
            return modal.getText().contains("Заказ оформлен") && modal.getText().contains("Номер заказа");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void orderSqooter() {
        setTomorrowDate();
        selectRentDuration();
        setColor();
        setComment();
        clickOrderButton();
        clickConfirmOrderButton();

        assertTrue("Модалка 'Заказ оформлен' не появилась", isOrderConfirmed());
    }




}
