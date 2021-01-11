import com.codeborne.selenide.*;
import com.codeborne.selenide.testng.SoftAsserts;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;

@Listeners({SoftAsserts.class})
public class SelenideBasics2Test {

    File downloadPath = new File("src/test/Downloads");
    File screenshotsPath = new File("src/test/Screenshots");

    @BeforeClass
    public void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 600;
        Configuration.assertionMode=AssertionMode.SOFT;
        Configuration.downloadsFolder = downloadPath.getAbsolutePath();
        Configuration.screenshots = true;
        Configuration.reportsFolder = screenshotsPath.getAbsolutePath();
        Configuration.fileDownload= FileDownloadMode.HTTPGET;
    }

    @Test
    public void testCase() {
        open("/books");

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
    public void testCase2() {
        open("/upload-download");

        try {
            $("#downloadButton").download();
        } catch (FileNotFoundException e) {
            System.out.println("no file");
        }


        sleep(2000);

//        if (file.exists()) {
//            System.out.println("Downloaded");
//        } else {
//            System.out.println("Not Downloaded");
//        }
    }
}
