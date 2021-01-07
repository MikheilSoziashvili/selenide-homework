import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

    @BeforeClass
    public void setup() {
        Configuration.startMaximized = true;
    }

    @Test(priority = 1)
    public void testCase() {
        open("http://the-internet.herokuapp.com/checkboxes");
        $$(byAttribute("type", "checkbox"))
                .get(0)
                .setSelected(true);
        $$(byAttribute("type", "checkbox"))
                .get(0)
                .shouldBe(Condition.checked);

        for (int i = 0; i < $$(byAttribute("type", "checkbox")).size(); i++) {
            $$(byAttribute("type", "checkbox"))
                    .get(i)
                    .shouldHave(Condition.type("checkbox"));
        }
        sleep(2000);
    }

    @Test(priority = 2)
    public void testCase2() {
        open("http://the-internet.herokuapp.com/dropdown");
//        Select select = new Select($(byId("dropdown")));
        $(byId("dropdown"))
                .shouldHave(Condition.selectedText(""))
                .selectOptionByValue("2");
        sleep(2000);
    }

    @Test(priority = 3)
    public void testCase3() {
        String[] values = {"Mikheil Soziashvili", "lulamiminaso17@gmail.com", "Address 1", "Address 2"};

        open("https://demoqa.com/text-box");
        $("#userName").setValue(values[0]);
        $("input[placeholder *= '@']").setValue(values[1]);
        $(byAttribute("placeholder","Current Address")).setValue(values[2]);
        $(byTagName("div")).$(byId("permanentAddress")).setValue(values[3]);
        $("#submit").click();

        List<SelenideElement> output  = $("#output").$$("div");

        for (int i = 0; i < output.size(); i++) {
            output.get(i).shouldHave(Condition.matchText(values[i]));
        }
    }
}
