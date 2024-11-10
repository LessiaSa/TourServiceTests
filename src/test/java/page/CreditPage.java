package page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CreditPage {
    private final SelenideElement headingCredit = $(withText("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));
    @Getter
    private final ElementsCollection notificationContent = $$(".notification__content");
    private final ElementsCollection inputSubElement = $$(".input__sub");


    public void creditPage() {
        headingCredit.shouldBe(visible);

    }


    public void detailsCard(Card validCard) {
        cardNumberField.setValue(validCard.getNumber());
        monthField.setValue(validCard.getMonth());
        yearField.setValue(validCard.getYear());
        holderField.setValue(validCard.getHolderName());
        cvcField.setValue(validCard.getCvc());
        continueButton.click();
    }


    public void checkSuccessNotification() {
        notificationContent.findBy(text("Операция одобрена Банком.")).shouldBe(visible, Duration.ofSeconds(15));
    }


    public String checkErrorNotification() {
        return String.valueOf(inputSubElement.findBy(text("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible));
    }

    public String checkInvalidFormat() {
        return String.valueOf(inputSubElement.findBy(text("Неверный формат")).shouldBe(visible));
    }

    public String checkRequiredField() {
        return String.valueOf(inputSubElement.findBy(text("Поле обязательно для заполнения")).shouldBe(visible));

    }

    public String checkInvalidDate() {
        return String.valueOf(inputSubElement.findBy(text("Неверно указан срок действия карты")).shouldBe(visible));
    }


    public String checkExpiredDate() {
        return String.valueOf(inputSubElement.findBy(text("Истёк срок действия карты")).shouldBe(visible));
    }

    public String checkInvalidName() {
        return String.valueOf(inputSubElement.findBy(text("ВВедите полное имя и фамилию")).shouldBe(visible));
    }

    public String checkLongName() {
        return String.valueOf(inputSubElement.findBy(text("Значение поля не может содержать более 100 символов")).shouldBe(visible));
    }

    public String checkInvalidDataName() {
        return String.valueOf(inputSubElement.findBy(text("Значение поля может содержать только буквы")).shouldBe(visible));
    }

    public String checkShortName() {
        return String.valueOf(inputSubElement.findBy(text("Значение поля должно содержать больше одной букв")).shouldBe(visible));
    }


    public void checkAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5)).shouldHave(CollectionCondition.texts("Неверный формат",
                "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат"));
    }
}

