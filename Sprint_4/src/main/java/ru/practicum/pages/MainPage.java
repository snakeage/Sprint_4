package ru.practicum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.practicum.util.EnvConfig.BASE_URL;
import static ru.practicum.util.EnvConfig.EXPLICITY_TIMEOUT;

public class MainPage {
    private final WebDriver driver;
    public static final By orderButtonInHeader = By.xpath("//div[contains(@class, 'Header_Nav__AGCXC')]//button[text()='Заказать']");
    public static final By orderButtonInMiddle = By.xpath("//div[contains(@class, 'Home_FinishButton__1_cWm')]//button[text()='Заказать']");
    private final By cookieAcceptButton = By.cssSelector("#rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnFaqQuestionButton(int index) {
        By questionButton = By.cssSelector("#accordion__heading-" + index);

        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(questionButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(button));

        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            // Пробуем кликнуть через JS как запасной вариант
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    public String getAnswerText(int index) {
        By answerLocator = By.xpath(String.format("//div[@id='accordion__panel-%d']/p", index));
        WebElement answer = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText().trim();
    }

    public void openMainPage() {
        driver.get(BASE_URL);
    }

    public void clickOrderButton(By orderButtonLocator) {
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(orderButtonLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    public void acceptCookiesIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                    .until(ExpectedConditions.elementToBeClickable(cookieAcceptButton))
                    .click();
        } catch (TimeoutException e) {

        }
    }
}
