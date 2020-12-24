package tests;

import com.qa.base.BaseTest;
import com.qa.pages.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

public class PaymentATMCardTest extends BaseTest {
    JSONObject loginUsers;
    OnboaringPage onboaringPage;
    LoginPage loginPage;
    SelectLocationPage selectLocationPage;
    SearchFoodPage searchFoodPage;
    PaymentATMCardPage paymentATMCardPage;

    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (datais != null) {
                datais.close();
            }
        }
        closeApp();
        launchApp();
    }
    @AfterClass
    public void afterClass() {}

    @BeforeMethod
    public void beforeMethod() {
        onboaringPage = new OnboaringPage();
        loginPage = new LoginPage();
        selectLocationPage = new SelectLocationPage();
        searchFoodPage = new SearchFoodPage();
        paymentATMCardPage = new PaymentATMCardPage();
        onboaringPage.OnboaringAllowNotification();
        loginPage.beforeLogin();
        loginPage.login(loginUsers.getJSONObject("validUser").getString("phone"),
                loginUsers.getJSONObject("validUser").getString("password"));
        selectLocationPage.SelectLocation(loginUsers.getJSONObject("validAddress").getString("address"));
        searchFoodPage.SearchFood(loginUsers.getJSONObject("validNameFood").getString("nameFood"));

    }
    @AfterMethod
    public void afterMethod() {
        closeApp();
        launchApp();
    }

    @Test
    public void invalidNumberATMCard() {
        paymentATMCardPage.PaymentATMCard(loginUsers.getJSONObject("invalidNumberCard").getString("numberCard"),
                loginUsers.getJSONObject("invalidNumberCard").getString("nameCard"),
                loginUsers.getJSONObject("invalidNumberCard").getString("dateCard"));
        Assert.assertTrue(false);
    }

    @Test
    public void invalidNameCardATM() {
        paymentATMCardPage.PaymentATMCard(loginUsers.getJSONObject("invalidNameCard").getString("numberCard"),
                loginUsers.getJSONObject("invalidNameCard").getString("nameCard"),
                loginUsers.getJSONObject("invalidNameCard").getString("dateCard"));
        Assert.assertTrue(false);
    }
    public void invalidDateCardATM() {
        paymentATMCardPage.PaymentATMCard(loginUsers.getJSONObject("invalidDateCard").getString("numberCard"),
                loginUsers.getJSONObject("invalidDateCard").getString("nameCard"),
                loginUsers.getJSONObject("invalidDateCard").getString("dateCard"));
        Assert.assertTrue(false);
    }

    @Test
    public void successfulPaymentATMCard() {
        paymentATMCardPage.PaymentATMCard(loginUsers.getJSONObject("validATMCard").getString("numberCard"),
                loginUsers.getJSONObject("validATMCard").getString("nameCard"),
                loginUsers.getJSONObject("validATMCard").getString("dateCard"));
        Assert.assertTrue(true);
    }

}
