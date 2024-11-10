package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {


    private DataHelper() {
    }

    public static Card getApprovedNumber() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    public static Card getDeclinedNumber() {
        return new Card("5555 6666 7777 8888", getCurrentMonth(), getCurrentYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //несуществующая карта
    public static Card getNonExistentNumberCard() {
        return new Card("1234 2222 3333 4444", getCurrentMonth(), getCurrentYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //недостающее количество символов в номере карты
    public static Card getMissingNumberOfCharactersInTheCardNumber() {
        return new Card("1111 2222 3333 444", getNextMonth(), getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //незаполненое поле"Номер карты"
    public static Card getEmptyFieldCardNumber() {
        return new Card(" ", getNextMonth(), getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //невалидный месяц - два 00
    public static Card getThereAreTwoZerosInTheMonthField() {
        return new Card("1111 2222 3333 4444", "00", getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //в поле"Месяц" одна цифра -0
    public static Card getMonthFieldOneDigitIsZero() {
        return new Card("1111 2222 3333 4444", "0", getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //в поле "Месяц" несуществующий месяц - цифры
    public static Card getThereIsANonEssentialValueInTheField() {
        return new Card("1111 2222 3333 4444", "17", getCurrentYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //в поле "Месяц" истекший срок действия карты - прошлый месяц
    public static Card getTheLastMonth() {
        return new Card("1111 2222 3333 4444", getLastMonth(), getCurrentYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //пустое поле "Месяц"
    public static Card getEmptyMonthField() {
        return new Card("1111 2222 3333 4444", " ", getNextYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //прошедший год в поле "Год"
    public static Card getLastYearInTheYearField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getLastYear(), getRandomUserEn(), getRandomCodeCVC());
    }

    //пустое поле "Год"
    public static Card getEmptyYearField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), " ", getRandomUserEn(), getRandomCodeCVC());
    }

    //далеко отстоящая дата из будущего
    public static Card getAFarOffDate() {
        return new Card("1111 2222 3333 4444", getNextMonth(), "89", getRandomUserEn(), getRandomCodeCVC());
    }

    //в поле "Владелец" только имя
    public static Card getInTheOwnerFieldOnlyTheName() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getNameEn(), getRandomCodeCVC());
    }

    //в поле "Владелец" имя и фамилия через дефис
    public static Card getFirstAndLastNamesSeparatedByHyphens() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getNameEn() + "-" + getSurnameEn(), getRandomCodeCVC());
    }

    //имя и фамилия в количестве 200 букв
    public static Card getHolderName200Letters() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getTooLongName(), getRandomCodeCVC());
    }

    //в поле "Владелец" только цифры
    public static Card getThereAreOnlyNumbersInTheOwnerField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getNumbersUsed() + " " + getNumbersUsed(), getRandomCodeCVC());
    }

    //в поле "Владелец" одна буква
    public static Card getThereIsOneLetterInTheOwnerField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getNameWithOneLetter(), getRandomCodeCVC());
    }

    //пустое поле "Владелец"
    public static Card getEmptyOwnerField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), " ", getRandomCodeCVC());
    }

    //вместо имени и фамилии - спецсимволы
    public static Card getSpecialCharactersInsteadOfFirstAndLastNames() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), "^%&#@*!" + " " + "*#@^%$", getRandomCodeCVC());
    }

    //в поле CVC одна цифра
    public static Card getThereIsCVCOneDigitInTheField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getRandomUserEn(), getRandomCodeCVCOneDigit());
    }

    //в поле CVC две цифры
    public static Card getThereAreTwoDigitsInTheField() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getRandomUserEn(), getRandomCodeCVCTwoDigit());
    }

    //поле CVC пустое
    public static Card getTheCVCFieldIsEmpty() {
        return new Card("1111 2222 3333 4444", getNextMonth(), getNextYear(), getRandomUserEn(), " ");
    }

    //все поля пустые
    public static Card getAllFieldAreEmpty() {
        return new Card(" ", " ", " ", " ", " ");
    }

    //текущий месяц
    public static String getCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    // прошлый месяц
    public static String getLastMonth() {
        LocalDate localDate = LocalDate.now();
        LocalDate lastMonth = localDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return lastMonth.format(formatter);
    }

    //следующий месяц
    public static String getNextMonth() {
        LocalDate localDate = LocalDate.now();
        LocalDate nextMonth = localDate.plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return nextMonth.format(formatter);
    }

    // текущий год
    public static String getCurrentYear() {
        return String.format("%ty", Year.now());
    }

    //в прошлом году
    public static String getLastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(1));
    }

    //в следующем году
    public static String getNextYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(1));
    }

    public static String getNameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    public static String getNameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String getSurnameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName();
    }

    public static String getSurnameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }


    public static String getRandomUserEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String getRandomUserRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getTooLongName() {
        Faker faker = new Faker();
        return faker.lorem().fixedString(200);
    }

    public static String getNumbersUsed() {
        Faker faker = new Faker();
        return faker.number().digits(9);
    }

    //имя из одной буквы
    public static String getNameWithOneLetter() {
        Faker faker = new Faker();
        return faker.lorem().fixedString(1);
    }

    public static String getRandomCodeCVC() {
        Faker faker = new Faker();
        return faker.numerify("###");

    }

    public static String getRandomCodeCVCOneDigit() {
        Faker faker = new Faker();
        return faker.numerify("#");
    }

    public static String getRandomCodeCVCTwoDigit() {
        Faker faker = new Faker();
        return faker.numerify("##");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeCVC {
        String code;
    }
}
