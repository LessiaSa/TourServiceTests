package page;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private static final SelenideElement heading = $$("h2").findBy(text("Путешествие дня"));
    private static final SelenideElement creditButton = $$("button").findBy(exactText("Купить в кредит"));

    public StartPage() {
        heading.shouldBe(visible);
    }

   public static void openCreditPage() {
        creditButton.click();
       heading.shouldBe(visible);
   }

}
