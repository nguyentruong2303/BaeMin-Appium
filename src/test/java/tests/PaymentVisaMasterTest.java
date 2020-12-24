package tests;

import com.qa.base.BaseTest;
import com.qa.pages.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

public class PaymentVisaMasterTest extends BaseTest {
    OnboaringPage onboaringPage;
    LoginPage loginPage;
    SelectLocationPage selectLocationPage;
    SearchFoodPage searchFoodPage;
    JSONObject loginUsers;
    PaymentVisaMasterPage paymentVisaMasterPage;

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
    public void afterClass(){}

    @BeforeMethod
    public void beforeMethod() {
        onboaringPage = new OnboaringPage();
        loginPage = new LoginPage();
        selectLocationPage = new SelectLocationPage();
        searchFoodPage = new SearchFoodPage();
        paymentVisaMasterPage = new PaymentVisaMasterPage();
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
    public void invalidNumberCardVisa() {
        paymentVisaMasterPage.PaymentVisaMaster(loginUsers.getJSONObject("invalidNumVisa").getString("Num"),
                loginUsers.getJSONObject("invalidNumVisa").getString("Date"),
                loginUsers.getJSONObject("invalidNumVisa").getString("Cvd"));
        Assert.assertTrue(false);
    }
    @Test
    public void invalidNameCardVisa() {
        paymentVisaMasterPage.PaymentVisaMaster(loginUsers.getJSONObject("invalidNameVisa").getString("Num"),
                loginUsers.getJSONObject("invalidNameVisa").getString("Date"),
                loginUsers.getJSONObject("invalidNameVisa").getString("Cvd"));
        Assert.assertTrue(false);
    }
    @Test
    public void invalidDateCardVisa() {
        paymentVisaMasterPage.PaymentVisaMaster(loginUsers.getJSONObject("invalidDateVisa").getString("Num"),
                loginUsers.getJSONObject("invalidDateVisa").getString("Date"),
                loginUsers.getJSONObject("invalidDateVisa").getString("Cvd"));
        Assert.assertTrue(false);
    }
    @Test
    public void invalidCvdCardVisa() {
        paymentVisaMasterPage.PaymentVisaMaster(loginUsers.getJSONObject("invalidCvdVisa").getString("Num"),
                loginUsers.getJSONObject("invalidCvdVisa").getString("Date"),
                loginUsers.getJSONObject("invalidCvdVisa").getString("Cvd"));
        Assert.assertTrue(false);
    }



    @Test
    public void successfulPaymentVisaMaster() {
        paymentVisaMasterPage.PaymentVisaMaster(loginUsers.getJSONObject("validVisa").getString("Num"),
                loginUsers.getJSONObject("validVisa").getString("Date"),
                loginUsers.getJSONObject("validVisa").getString("Cvd"));
        Assert.assertTrue(true);
    }
}
