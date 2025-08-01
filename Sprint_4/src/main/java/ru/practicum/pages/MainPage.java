package ru.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.practicum.util.EnvConfig.BASE_URL;
import static ru.practicum.util.EnvConfig.EXPLICITY_TIMEOUT;

public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnFaqQuestionButton(int index) {
        By questionButton = By.cssSelector("#accordion__heading-" + index);
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(questionButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
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
}
