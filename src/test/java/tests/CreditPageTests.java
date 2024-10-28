package tests;

import data.Card;
import data.SQLHelper;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import page.CreditPage;
import page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;

public class CreditPageTests {

    @BeforeEach
    void setUp() {
      SQLHelper.clearTables();
      String url = System.getProperty("sut.url");
      open(url);
    }

    @Test
    @Order(1)
    void shouldSuccessNotification() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidCvc(), getRandomUserEn());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.detailsCard(card);
        creditPage.checkSuccessNotification();
        Assertions.assertEquals("APPROVED",SQLHelper.getCreditStatus());
    }



}
