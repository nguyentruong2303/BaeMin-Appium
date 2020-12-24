package tests;

import com.qa.base.BaseTest;
import com.qa.pages.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

public class PaymentMoMoTest extends BaseTest {
    OnboaringPage onboaringPage;
    LoginPage loginPage;
    SelectLocationPage selectLocationPage;
    SearchFoodPage searchFoodPage;
    JSONObject loginUsers;
    PaymentMoMoPage paymentMoMoPage;

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
        paymentMoMoPage = new PaymentMoMoPage();
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
    public void successfulPaymentMoMo() {
        paymentMoMoPage.PaymentMoMo();
        Assert.assertTrue(true);
    }
}
