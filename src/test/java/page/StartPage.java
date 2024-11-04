package page;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private final SelenideElement heading = $("[App_appContainer__3jRx1]");

    public StartPage() {
        heading.shouldHave(text("Путешествие дня")).shouldBe(visible);
    }

    public static CreditPage buyOnCredit() {
        SelenideElement creditButton = $$("button").findBy(text("Купить в кредит"));
        creditButton.click();
        return new CreditPage();
    }

    public PaymentPage buy() {
        SelenideElement buyButton = $$("button").findBy(text("Купить"));
        buyButton.click();
        return new PaymentPage();
    }

}
