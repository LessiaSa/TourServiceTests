package page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private static SelenideElement heading = $$("h3").findBy(Condition.text("Кредит по данным карты"));
    private static SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000'] input");
    private static SelenideElement monthField = $("[placeholder='08'] input");
    private static SelenideElement yearField = $("[placeholder='22'] input");
    private static SelenideElement holderField = $("[autocomplete='on'] input");
    private static SelenideElement cvcField = $("[placeholder='999'] input");
    private static SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));
    private static SelenideElement errorNotification = $("['error-notification'].notification__content");

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }


    public static StartPage detailsCard(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolderName());
        cvcField.setValue(card.getCvc());
        continueButton.click();
        return new StartPage();
    }


    public void checkSuccessNotification() {
        $(".notification__content").shouldHave(exactText("Операция одобрена Банком.")).shouldBe(visible);
    }


    public void checkDeclineNotification() {
        $("notification__content").shouldHave(exactText("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible);
    }

    public void checkInvalidFormat() {
        $(".input__sub").shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void checkRequiredField() {
        $(".input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    public void checkInvalidDate() {
        $(".input__sub").shouldHave(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void checkExpiredDate() {
        $(".input__sub").shouldHave(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void checkInvalidName() {
        $(".input__sub").shouldHave(exactText("Введите полное имя и фамилию")).shouldBe(visible);
    }

    public void checkLongName() {
        $(".input__sub").shouldHave(exactText("Значение поля не может содержать более 100 символов")).shouldBe(visible);
    }

    public void checkInvalidDataName() {
        $(".input__sub").shouldHave(exactText("Значение поля может содержать только буквы и дефис")).shouldBe(visible);
    }

    public void checkShortName() {
        $(".input__sub").shouldHave(exactText("Значение поля должно содержать больше одной буквы")).shouldBe(visible);
    }

    public void checkInvalidCvc() {
        $(".input__sub").shouldHave(exactText("Значение поля должно содержать 3 цифры")).shouldBe(visible);
    }

    public void checkAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5)).shouldHave(CollectionCondition.texts(
                "Поле обязательно для заполнения"));
    }
}

