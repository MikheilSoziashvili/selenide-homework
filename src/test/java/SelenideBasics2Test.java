import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.testng.ScreenShooter;
import com.codeborne.selenide.testng.SoftAsserts;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

@Listeners({SoftAsserts.class, ScreenShooter.class})
public class SelenideBasics2Test {

    File downloadPath = new File("src/test/Downloads");
    File screenshotsPath = new File("src/test/Screenshots");

    @BeforeClass
    public void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 7000;
        Configuration.assertionMode=AssertionMode.SOFT;
        Configuration.downloadsFolder = downloadPath.getAbsolutePath();
        Configuration.screenshots = true;
        Configuration.reportsFolder = screenshotsPath.getAbsolutePath();
        Configuration.proxyEnabled = true;
        Configuration.fileDownload= FileDownloadMode.PROXY;
    }

    @Test
    public void testCase() {
        open("/books");

        ScreenShooter.captureSuccessfulTests = true;

        // Failed Case
        Condition publisherAndTitle = Condition.and("titleAndPublisher", Condition.text("JavaScript"), Condition.text("O'Reilly Media"));
        $(".rt-tbody").findAll(".rt-tr-group")
                .filterBy(publisherAndTitle)
                .shouldHave(size(10));

        // Success Case
        for (int i = 0; i < $$(".rt-tr-group").size()-2; i++) {
            // -2 იმიტომ რომ დროში ვიყავი შეზღუდული და უფრო დინამიური გზა ვერ ვიპოვე
            // ბოლო 2 ელემენტი ცარიელია
            $$(".rt-tr-group").get(i).findAll(".rt-td").get(0).$("img").shouldHave(Condition.image);
        }
    }
    @Test
    public void testCase2() throws FileNotFoundException {
        open("/upload-download");
        File file = $(byLinkText("Download")).download();

        if (file.exists()) {
            System.out.println("downloaded");
        } else {
            System.out.println("not downloaded");
        }
    }
}
