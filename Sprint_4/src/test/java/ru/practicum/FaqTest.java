package ru.practicum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.pages.MainPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static ru.practicum.util.TestData.FAQ_DATA;

@RunWith(Parameterized.class)
public class FaqTest {

private final int questionIndex;
private final String expectedAnswer;

    @Rule
    public DriverFactory factory = new DriverFactory();

    public FaqTest(int questionIndex, String expectedAnswer) {
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "Вопрос #{0} должен показать ответ: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(FAQ_DATA);
    }

    @Test
    public void testCheckFaqAnswer() {
        WebDriver driver = factory.getDriver();

        MainPage mainPage = new MainPage(driver);

        mainPage.openMainPage();
        mainPage.acceptCookiesIfPresent();

        mainPage.clickOnFaqQuestionButton(questionIndex);
        String actualAnswer = mainPage.getAnswerText(questionIndex);

        assertEquals(expectedAnswer, actualAnswer);
    }
}
