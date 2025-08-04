package ru.practicum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.practicum.util.EnvConfig.BASE_URL;
import static ru.practicum.util.EnvConfig.EXPLICITY_TIMEOUT;

public class MainPage {
    private final WebDriver driver;
    // Кнопка "Заказать" в шапке сайта
    private final By orderButtonInHeader = By.xpath("//div[contains(@class, 'Header_Nav__AGCXC')]//button[text()='Заказать']");
    // Кнопка "Заказать" в средней части главной страницы
    private final By orderButtonInMiddle = By.xpath("//div[contains(@class, 'Home_FinishButton__1_cWm')]//button[text()='Заказать']");
    // Кнопка согласия с cookie
    private final By cookieAcceptButton = By.cssSelector("#rcc-confirm-button");

    // Шаблоны локаторов для FAQ: вопросы (по CSS селектору) и ответы (по XPath)
    private final String faqQuestionCssTemplate = "#accordion__heading-%d";
    private final String faqAnswerXpathTemplate = "//div[@id='accordion__panel-%d']/p";

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Открывает главную страницу по базовому URL
    public void openMainPage() {
        driver.get(BASE_URL);
    }

    // Универсальный метод для клика по кнопкам с ожиданием и скроллом к элементу
    public void clickButton(By locator) {
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        button.click();
    }

    // Кликает по кнопке заказа в шапке
    public void clickOrderButtonInHeader(){
        clickButton(orderButtonInHeader);
    }

    // Кликает по вопросу FAQ по индексу, раскрывая ответ
    public void clickOrderButtonInMiddle(){
        clickButton(orderButtonInMiddle);
    }

    public void clickOnFaqQuestionButton(int index) {
        By questionButton = By.cssSelector(String.format(faqQuestionCssTemplate, index));

        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(questionButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            // Пробуем кликнуть через JS как запасной вариант
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    // Получает текст ответа FAQ по индексу вопроса
    public String getAnswerText(int index) {
        By answerLocator = By.xpath(String.format(faqAnswerXpathTemplate, index));
        WebElement answer = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText().trim();
    }

    // Принимает куки, если появляется соответствующая кнопка
    public void acceptCookiesIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(EXPLICITY_TIMEOUT))
                    .until(ExpectedConditions.elementToBeClickable(cookieAcceptButton))
                    .click();
        } catch (TimeoutException e) {
            // Кнопка не появилась — пропускаем
        }
    }
}
