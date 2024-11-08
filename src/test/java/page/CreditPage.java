package page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private static final SelenideElement headingCredit = $$("button").findBy(text("Кредит по данным карты"));
    private static final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private static final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private static final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private static final SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private static final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private static final SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));


    public CreditPage() {
        headingCredit.shouldBe(visible);

    }



    public static void detailsCard(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolderName());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }


    public static void checkSuccessNotification() {
        $(".notification__content").shouldHave(exactText("Операция одобрена Банком.")).shouldBe(visible);
    }


    public static void checkDeclineNotification() {
        $(".notification__content").shouldHave(exactText("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible);
    }

    public static void checkErrorNotification() {
        $(".notification__content").shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    }

    public static void checkInvalidFormat() {
        $(".input__sub").shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public static void checkRequiredField() {
        $(".input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    public static void checkInvalidDate() {
        $(".input__sub").shouldHave(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }


    public static void checkExpiredDate() {
        $(".input__sub").shouldHave(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public static void checkInvalidName() {
        $(".input__sub").shouldHave(exactText("Введите полное имя и фамилию")).shouldBe(visible);
    }

    public static void checkLongName() {
        $(".input__sub").shouldHave(exactText("Значение поля не может содержать более 100 символов")).shouldBe(visible);
    }

    public static void checkInvalidDataName() {
        $(".input__sub").shouldHave(exactText("Значение поля может содержать только буквы")).shouldBe(visible);
    }

    public static void checkShortName() {
        $(".input__sub").shouldHave(exactText("Значение поля должно содержать больше одной буквы")).shouldBe(visible);
    }


    public static void checkAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5)).shouldHave(CollectionCondition.texts("Неверный формат",
                "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат"));
    }
}

