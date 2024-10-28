package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private final SelenideElement heading = $$("h2").findBy(Condition.text("Путешествие дня"));
    private final SelenideElement buyButton = $$("button").findBy(Condition.text("Купить"));
    private final SelenideElement creditButton = $$("button").findBy(Condition.text("Купить в кредит"));

    public StartPage() {
        heading.shouldBe(Condition.visible);
    }

    public CreditPage buyInCredit() {
        creditButton.click();
        return new CreditPage();
    }

    public PaymentPage buy() {
        buyButton.click();
        return new PaymentPage();
    }

}