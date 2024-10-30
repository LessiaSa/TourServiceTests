package data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {


    private DataHelper () {
    }

    public static String getApprovedNumber() {
        return "111 2222 3333 4444";
    }

    public static String getDeclinedNumber() {
        return "5555 6666 7777 8888";
    }

    public static String getCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }


    public static String getLastMonth() {
        LocalDate localDate = LocalDate.now();
        LocalDate lastMonth = localDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String monthValue = lastMonth.format(formatter);
        return monthValue;
    }

    public static String getCurrentYear() {
        return String.format("%ty", Year.now());
    }

    public static String getLastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(1));
    }

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
        Faker faker= new Faker(new Locale("ru"));
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

    public static String getNameWithOneLetter() {
        Faker faker = new Faker();
        return faker.lorem().characters(1);
    }

    public static CodeCVC generateRandomCodeCVC() {
        Faker faker = new Faker();
        return new CodeCVC(faker.numerify("###"));

    }

    public static CodeCVC generateRandomCodeCVCOneDigit() {
        Faker faker = new Faker();
        return new CodeCVC(faker.numerify("#"));
    }

    public static CodeCVC generateRandomCodeCVCTwoDigit() {
        Faker faker = new Faker();
        return new CodeCVC(faker.numerify("##"));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeCVC {
        String code;
    }
}
