package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private static final SelenideElement heading = $$("h2").findBy(text("Путешествие дня"));
    private static final SelenideElement creditButton = $$("button").findBy(exactText("Купить в кредит"));

    public void startPage() {
        heading.shouldBe(visible);
    }

    public CreditPage openCreditPage() {
        creditButton.click();
        return new CreditPage();
    }

}
