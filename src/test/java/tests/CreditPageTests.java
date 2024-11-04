package tests;

import data.Card;
import data.DataHelper;
import page.StartPage;
import org.junit.jupiter.api.*;
import page.CreditPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanTables;


public class CreditPageTests {
    CreditPage creditPage;
    private data.Card Card;



    //для очистки базы данных после выполнения всех автотестов(после этого надо перезапустить джарник0
    @AfterAll
    static void tearDownAll() throws SQLException {
        cleanTables();
    }


    @BeforeEach
    void setUp() {
        creditPage = open("http://localhost:8080", CreditPage.class);

    }

    //для драйвера
    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/windows/chromedriver-win64");
    }

    @Test
    @DisplayName("Успешная обработка данных")
    void shouldSuccessfulDataProcessing() {
        StartPage.buyOnCredit();
        DataHelper.getApprovedNumber();
        DataHelper.getNextMonth();
        DataHelper.getNextYear();
        DataHelper.getRandomUserEn();
        DataHelper.getRandomCodeCVC();
        CreditPage.detailsCard(Card);
        creditPage.checkSuccessNotification();
    }

}
