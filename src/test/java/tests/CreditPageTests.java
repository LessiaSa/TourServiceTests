package tests;

import data.Card;
import data.SQLHelper;
import lombok.val;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static data.SQLHelper.cleanDatabase;

public class CreditPageTests {
    CreditPage creditPage;

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        creditPage = open("http://localhost:8080", CreditPage.class);

    }

    @Test
    @DisplayName("Успешная обработка данных")
    void shouldSuccessfulDataProcessing() {


    }



}
