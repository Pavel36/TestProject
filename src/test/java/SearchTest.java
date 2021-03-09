import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SearchTest {
    @Test
    public void SearchTest() {

        //Определяем путь к райверу (использвоал яндексбраузер, тк с хромом есть проблемы, драйвер для яндекса брал здесь: https://github.com/yandex/YandexDriver)
        System.setProperty("webdriver.chrome.driver","C:\\yandexdriver.exe");
        ChromeDriver driver = new ChromeDriver();

        //Задаём время ожидания загрузки элемента на странице
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

        //Задаём время ожидания загрузки страницы (для всех старниц)
        driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);

        //Переходим на страницу istqb
        driver.get("https://www.istqb.org/");

        //Ищем на странице строку поиска (скопировал Xpath данного элемента из браузера)
        WebElement searchInput = driver.findElementByXPath("//*[@id=\"mod-search-searchword\"]");

        //Направляем в поисковую строку запрос
        searchInput.sendKeys("Foundation Level 2018 \n");

        //Проверяем, что результат поиска не пустой
        try {
            //Проверяем, что появился список результатов поиска
            driver.findElementByXPath("//*[@id=\"t3-content\"]/div[2]/dl");
        } catch (Exception e) {
            throw new Error("Search result is empty");
        }

        //Выбираем из списка результатов раздел с текстом Foundation Level Automotive Software Tester
        WebElement searchItem = driver.findElementByXPath("//*[contains(text(), 'Foundation Level Automotive Software Tester')]");

        //Получаем атрибут href строки с нужным результатом и переходим по данной ссылке
        driver.get(searchItem.getAttribute("href"));

        //Убеждаемся, что заголовок полученной страницы соответствует заданной
        Assert.assertTrue(driver.getTitle().contains("Foundation Level Automotive Software Tester"));

        //Завершаем тест
        driver.quit();
    }
}
