package ru.azavialov;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class DemoQATest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 5000;
    }

    @Test
    void testRegisterForm() {
        String name = "Anna";
        String lastName = "Ivanova";
        String email = "aivanova@mail.ru";
        String gender = "Female";
        String phone = "9999123456";
        int yearOfBirth = 2001;
        String monthOfBirth = "March";
        String dayOfBirth = "27";
        String[] hobbies = new String[]{"Reading", "Music"};
        String picture = "Capibara.jpg";
        String address = "Lenina 169 - 228";
        String state = "Uttar Pradesh";
        String city = "Merrut";

        //Preconditions
        open("/automation-practice-form");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        //Form filling
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).parent().click();
        $("#userNumber").setValue(phone);
        setCalendarDate(yearOfBirth, monthOfBirth, dayOfBirth);
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#subjectsInput").setValue("E");
        $(withText("conomics")).click();
        setHobbies(hobbies);
        $("#uploadPicture").uploadFromClasspath(picture);
        $("#currentAddress").setValue(address);
        selectInDropdownList("#state", state);
        selectInDropdownList("#city", city);
        $("#submit").click();

        //Checking submit results
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $$(".table td:nth-of-type(1)").shouldHave(texts("Student Name", "Student Email", "Gender", "Mobile",
                "Date of Birth", "Subjects", "Hobbies", "Picture",
                "Address", "State and City"));
        $$(".table td:nth-of-type(2)").shouldHave(texts(name + " " + lastName, email, gender, phone,
                dayOfBirth + " " + monthOfBirth + "," + yearOfBirth, "Maths, Economics", String.join(", ", hobbies), picture,
                address, state + " " + city));
        $("#closeLargeModal").shouldBe(visible);

    }

    void setCalendarDate(int year, String month, String date) {
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").click();
        $(byText(Integer.toString(year))).click();
        $(".react-datepicker__month-select").click();
        $(byText(month)).click();
        String dateSelector = ".react-datepicker__day--0" + date;
        $(dateSelector + "[aria-label *= " + month + "]").click();
    }

    void setHobbies(String[] hobbiesList){
        for (String hobby : hobbiesList){
            $(byText(hobby)).parent().click();
        }
    }

    void selectInDropdownList(String selector, String item){
        $(selector).click();
        $(selector).$(byText(item)).click();
    }

}


