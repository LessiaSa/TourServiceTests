package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanTables;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditPageTests {
    private final CreditPage creditPage = new CreditPage();
    private final StartPage startPage = new StartPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAllAllure() {
        SelenideLogger.removeListener("allure");
    }


    //для очистки базы данных после выполнения всех автотестов(после этого надо перезапустить джарник
    @AfterAll
    static void tearDownAll() throws SQLException {
        cleanTables();
    }

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }



    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        startPage.startPage();
        startPage.openCreditPage();
        creditPage.creditPage();

    }


    @Test
    @DisplayName("Успешная обработка данных")
    void shouldSuccessfulDataProcessing() {
        var validCard = DataHelper.getApprovedNumber();
        creditPage.detailsCard(validCard);
        creditPage.checkSuccessNotification();
        assertEquals("APPROVED", SQLHelper.getLastPayOnCreditUserStatusMySQL());
    }


    @Test
    @DisplayName("Операция отклонена при вводе валидных данных во всех полях")
    void shouldOperationWasRejectedWhenValidDataWasEnteredInAllFields() {
        var validCard = DataHelper.getDeclinedNumber();
        creditPage.detailsCard(validCard);
        creditPage.checkErrorNotification();
        assertEquals("DECLINED", SQLHelper.getLastPayOnCreditUserStatusMySQL());
    }

    @Test
    @DisplayName("В поле 'Номер карты' несуществующий номер карты")
    void shouldThereIsANonExistentNumberInTheCardNumberField() {
        var nonExistentCard = DataHelper.getNonExistentNumberCard();
        creditPage.detailsCard(nonExistentCard);
        creditPage.checkErrorNotification();
    }

    @Test
    @DisplayName("Недостает символов в номере карты")
    void shouldMissingCharactersInTheCardNumber() {
        var missingCharactersNumberCard = DataHelper.getMissingNumberOfCharactersInTheCardNumber();
        creditPage.detailsCard(missingCharactersNumberCard);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Незаполненое поле 'Номер карты'")
    void shouldTheBlankFieldCardNumber() {
        var blankFieldNumber = DataHelper.getEmptyFieldCardNumber();
        creditPage.detailsCard(blankFieldNumber);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле'Месяц' два ноля")
    void shouldThereAreTwoZerosInTheFieldMonth() {
        var twoZerosInMonth = DataHelper.getThereAreTwoZerosInTheMonthField();
        creditPage.detailsCard(twoZerosInMonth);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Месяц' недостаточно цифр")
    void shouldThereAreNotEnoughNumbersInTheMonthField() {
        var notEnoughNumbersMonth = DataHelper.getMonthFieldOneDigitIsZero();
        creditPage.detailsCard(notEnoughNumbersMonth);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Несуществующий месяц")
    void shouldANonExistentMonth() {
        var nonExistentMonth = DataHelper.getThereIsANonEssentialValueInTheField();
        creditPage.detailsCard(nonExistentMonth);
        creditPage.checkInvalidDate();
    }

    @Test
    @DisplayName("Истекший месяц")
    void shouldThePastMonth() {
        var pastMonth = DataHelper.getTheLastMonth();
        creditPage.detailsCard(pastMonth);
        creditPage.checkInvalidDate();
    }

    @Test
    @DisplayName("Пустое поле 'Месяц'")
    void shouldEmptyMonthField() {
        var emptyMonth = DataHelper.getEmptyMonthField();
        creditPage.detailsCard(emptyMonth);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Год'  истекший срок")
    void shouldInTheYearFieldTheExpiredPeriod() {
        var expiredPeriodYear = DataHelper.getLastYearInTheYearField();
        creditPage.detailsCard(expiredPeriodYear);
        creditPage.checkExpiredDate();
    }

    @Test
    @DisplayName("Пустое поле 'Год'")
    void shouldEmptyYearField() {
        var emptyYear = DataHelper.getEmptyYearField();
        creditPage.detailsCard(emptyYear);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Год' далеко отстоящая дата")
    void shouldYearFieldDateIsFarAway() {
        var dateAway = DataHelper.getAFarOffDate();
        creditPage.detailsCard(dateAway);
        creditPage.checkInvalidDate();
    }

    @Test
    @DisplayName("В поле 'Владелец' только имя")
    void shouldInTheOwnerFieldOnlyTheName() {
        var onlyName = DataHelper.getInTheOwnerFieldOnlyTheName();
        creditPage.detailsCard(onlyName);
        creditPage.checkInvalidName();
    }

    @Test
    @DisplayName("В поле 'Владелец'  имя и фамилия через дефис")
    void shouldOwnerFieldFirstAndLastNameSeparatedByAHyphen() {
        var ownerNameSeparatedHyphen = DataHelper.getFirstAndLastNamesSeparatedByHyphens();
        creditPage.detailsCard(ownerNameSeparatedHyphen);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Имя и фамилия из 200 букв")
    void shouldFirstAndLastnameOf200Letters() {
        var longFirstAndLastName = DataHelper.getHolderName200Letters();
        creditPage.detailsCard(longFirstAndLastName);
        creditPage.checkLongName();
    }

    @Test
    @DisplayName("В поле 'Владелец' только цифры")
    void shouldThereAreOnlyNumbersInTheOwnerField() {
        var ownerInNumbers = DataHelper.getThereAreOnlyNumbersInTheOwnerField();
        creditPage.detailsCard(ownerInNumbers);
        creditPage.checkInvalidDataName();
    }

    @Test
    @DisplayName("В поле 'Владелец' одна буква")
    void shouldThereIsOneLetterInOwnerField() {
        var ownerOneLetter = DataHelper.getThereIsOneLetterInTheOwnerField();
        creditPage.detailsCard(ownerOneLetter);
        creditPage.checkShortName();
    }

    @Test
    @DisplayName("Пустое поле 'Владелец'")
    void shouldEmptyFieldOwner() {
        var emptyOwnerField = DataHelper.getEmptyOwnerField();
        creditPage.detailsCard(emptyOwnerField);
        creditPage.checkRequiredField();
    }

    @Test
    @DisplayName("Вместо имени и фамилии спецсимволы")
    void shouldSpecialCharactersInsteadOfFirstAndLastName() {
        var specialCharactersName = DataHelper.getSpecialCharactersInsteadOfFirstAndLastNames();
        creditPage.detailsCard(specialCharactersName);
        creditPage.checkInvalidDataName();
    }

    @Test
    @DisplayName("В поле 'CVC/CVV' одна цифра")
    void shouldThereIsOneDigitInTheCVCField() {
        var oneDigitCVC = DataHelper.getThereIsCVCOneDigitInTheField();
        creditPage.detailsCard(oneDigitCVC);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'CVC' две цифры")
    void shouldThereAreTwoDigitsInTheCVCField() {
        var twoDigitCVC = DataHelper.getThereAreTwoDigitsInTheField();
        creditPage.detailsCard(twoDigitCVC);
        creditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Пустое поле CVC")
    void shouldEmptyCVCField() {
        var emptyCVC = DataHelper.getTheCVCFieldIsEmpty();
        creditPage.detailsCard(emptyCVC);
        creditPage.checkRequiredField();
    }

    @Test
    @DisplayName("Пустая заявка")
    void shouldAnEmptyBottle() {
        var emptyBottle = DataHelper.getAllFieldAreEmpty();
        creditPage.detailsCard(emptyBottle);
        creditPage.checkAllFieldsAreRequired();
    }
}
