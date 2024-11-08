package tests;

import data.DataHelper;
import data.SQLHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import page.CreditPage;
import page.StartPage;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanTables;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.CreditPage.detailsCard;


public class CreditPageTests {

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

    }

    @Test
    @DisplayName("Успешная обработка данных")
    void shouldSuccessfulDataProcessing() {
        var validCard = DataHelper.getApprovedNumber();
        StartPage.openCreditPage();
        detailsCard(validCard);
        CreditPage.checkSuccessNotification();
        assertEquals("APPROVED", SQLHelper.getLastPayOnCreditUserStatusMySQL());
    }

    @Test
    @DisplayName("Операция отклонена при вводе валидных данных во всех полях")
    void shouldOperationWasRejectedWhenValidDataWasEnteredInAllFields() {
        var validCard = DataHelper.getDeclinedNumber();
        StartPage.openCreditPage();
        detailsCard(validCard);
        CreditPage.checkDeclineNotification();
        assertEquals("DECLINED",SQLHelper.getLastPayOnCreditUserStatusMySQL());
    }

    @Test
    @DisplayName("В поле 'Номер карты' несуществующий номер карты")
    void shouldThereIsANonExistentNumberInTheCardNumberField() {
        var nonExistentCard = DataHelper.getNonExistentNumberCard();
        StartPage.openCreditPage();
        detailsCard(nonExistentCard);
        CreditPage.checkErrorNotification();
        assertEquals(" ", SQLHelper.countRecords());

    }

    @Test
    @DisplayName("Недостает символов в номере карты")
    void shouldMissingCharactersInTheCardNumber() {
        var missingCharactersNumberCard = DataHelper.getMissingNumberOfCharactersInTheCardNumber();
        StartPage.openCreditPage();
        detailsCard(missingCharactersNumberCard);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Незаполненое поле 'Номер карты'")
    void shouldTheBlankFieldCardNumber() {
        var blankFieldNumber = DataHelper.getEmptyFieldCardNumber();
        StartPage.openCreditPage();
        detailsCard(blankFieldNumber);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле'Месяц' два ноля")
    void shouldThereAreTwoZerosInTheFieldMonth() {
        var twoZerosInMonth = DataHelper.getThereAreTwoZerosInTheMonthField();
        StartPage.openCreditPage();
        detailsCard(twoZerosInMonth);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Месяц' недостаточно цифр")
    void shouldThereAreNotEnoughNumbersInTheMonthField() {
        var notEnoughNumbersMonth = DataHelper.getMonthFieldOneDigitIsZero();
        StartPage.openCreditPage();
        detailsCard(notEnoughNumbersMonth);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Несуществующий месяц")
    void shouldANonExistentMonth() {
        var nonExistentMonth = DataHelper.getThereIsANonEssentialValueInTheField();
        StartPage.openCreditPage();
        detailsCard(nonExistentMonth);
        CreditPage.checkInvalidDate();
    }

    @Test
    @DisplayName("Истекший месяц")
    void shouldThePastMonth() {
        var pastMonth = DataHelper. getTheLastMonth();
        StartPage.openCreditPage();
        detailsCard(pastMonth);
        CreditPage. checkInvalidDate();
    }

    @Test
    @DisplayName("Пустое поле 'Месяц'")
    void shouldEmptyMonthField() {
        var emptyMonth = DataHelper.getEmptyMonthField();
        StartPage.openCreditPage();
        detailsCard(emptyMonth);
        CreditPage. checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Год'  истекший срок")
    void shouldInTheYearFieldTheExpiredPeriod() {
        var expiredPeriodYear = DataHelper.getLastYearInTheYearField();
        StartPage.openCreditPage();
        detailsCard(expiredPeriodYear);
        CreditPage.checkExpiredDate();
    }

    @Test
    @DisplayName("Пустое поле 'Год'")
    void shouldEmptyYearField() {
        var emptyYear = DataHelper.getEmptyYearField();
        StartPage.openCreditPage();
        detailsCard(emptyYear);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'Год' далеко отстоящая дата")
    void shouldYearFieldDateIsFarAway() {
        var dateAway = DataHelper.getAFarOffDate();
        StartPage.openCreditPage();
        detailsCard(dateAway);
        CreditPage.checkInvalidDate();
    }

    @Test
    @DisplayName("В поле 'Владелец' только имя")
    void shouldInTheOwnerFieldOnlyTheName() {
        var onlyName = DataHelper.getInTheOwnerFieldOnlyTheName();
        StartPage.openCreditPage();
        detailsCard(onlyName);
        CreditPage.checkInvalidName();
    }

    @Test
    @DisplayName("В поле 'Владелец'  имя и фамилия через дефис")
    void shouldOwnerFieldFirstAndLastNameSeparatedByAHyphen() {
        var ownerNameSeparatedHyphen = DataHelper.getFirstAndLastNamesSeparatedByHyphens();
        StartPage.openCreditPage();
        detailsCard(ownerNameSeparatedHyphen);
        CreditPage. checkInvalidFormat();
    }

    @Test
    @DisplayName("Имя и фамилия из 200 букв")
    void shouldFirstAndLastnameOf200Letters() {
        var longFirstAndLastName = DataHelper.getHolderName200Letters();
        StartPage.openCreditPage();
        detailsCard(longFirstAndLastName);
        CreditPage.checkLongName();
    }

    @Test
    @DisplayName("В поле 'Владелец' только цифры")
    void shouldThereAreOnlyNumbersInTheOwnerField() {
        var ownerInNumbers = DataHelper.getThereAreOnlyNumbersInTheOwnerField();
        StartPage.openCreditPage();
        detailsCard(ownerInNumbers);
        CreditPage.checkInvalidDataName();
    }

    @Test
    @DisplayName("В поле 'Владелец' одна буква")
    void shouldThereIsOneLetterInOwnerField() {
        var ownerOneLetter = DataHelper.getThereIsOneLetterInTheOwnerField();
        StartPage.openCreditPage();
        detailsCard(ownerOneLetter);
        CreditPage.checkShortName();
    }

    @Test
    @DisplayName("Пустое поле 'Владелец'")
    void shouldEmptyFieldOwner() {
        var emptyOwnerField = DataHelper.getEmptyOwnerField();
        StartPage.openCreditPage();
        detailsCard(emptyOwnerField);
        CreditPage.checkRequiredField();
    }

    @Test
    @DisplayName("Вместо имени и фамилии спецсимволы")
    void shouldSpecialCharactersInsteadOfFirstAndLastName() {
        var specialCharactersName = DataHelper.getSpecialCharactersInsteadOfFirstAndLastNames();
        StartPage.openCreditPage();
        detailsCard(specialCharactersName);
        CreditPage.checkInvalidDataName();
    }

    @Test
    @DisplayName("В поле 'CVC/CVV' одна цифра")
    void shouldThereIsOneDigitInTheCVCField() {
        var oneDigitCVC = DataHelper.getThereIsCVCOneDigitInTheField();
        StartPage.openCreditPage();
        detailsCard(oneDigitCVC);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("В поле 'CVC' две цифры")
    void shouldThereAreTwoDigitsInTheCVCField() {
        var twoDigitCVC = DataHelper.getThereAreTwoDigitsInTheField();
        StartPage.openCreditPage();
        detailsCard(twoDigitCVC);
        CreditPage.checkInvalidFormat();
    }

    @Test
    @DisplayName("Пустое поле CVC")
    void shouldEmptyCVCField() {
        var emptyCVC = DataHelper.getTheCVCFieldIsEmpty();
        StartPage.openCreditPage();
        detailsCard(emptyCVC);
        CreditPage.checkRequiredField();
    }

    @Test
    @DisplayName("Пустая завка")
    void shouldAnEmptyBottle() {
        var emptyBottle = DataHelper.getAllFieldAreEmpty();
        StartPage.openCreditPage();
        detailsCard(emptyBottle);
        CreditPage.checkAllFieldsAreRequired();
    }

}
