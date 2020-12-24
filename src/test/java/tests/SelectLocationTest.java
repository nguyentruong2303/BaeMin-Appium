package tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.OnboaringPage;
import com.qa.pages.SelectLocationPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SelectLocationTest extends BaseTest {
    LoginPage loginPage;
    OnboaringPage onboaringPage;
    JSONObject loginUsers;
    TestUtils utils;
    SelectLocationPage selectLocationPage;

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
    public void beforeMethod(Method method) {
        //utils.log().info("Starting test", method.getName());
        onboaringPage = new OnboaringPage();
        loginPage = new LoginPage();
        selectLocationPage = new SelectLocationPage();
        onboaringPage.OnboaringAllowNotification();
        loginPage.beforeLogin();
        loginPage.login(loginUsers.getJSONObject("validUser").getString("phone"),
                loginUsers.getJSONObject("validUser").getString("password"));

    }
    @AfterMethod
    public void afterMethod() {
        closeApp();
        launchApp();
    }
    @Test
    public void invalidLocation() {
        selectLocationPage.SelectLocation(loginUsers.getJSONObject("invalidAddress").getString("address"));
        Assert.assertTrue(false);
    }
    @Test
    public void validLocation() {
        selectLocationPage.SelectLocation(loginUsers.getJSONObject("validAddress").getString("address"));
        Assert.assertTrue(true);
    }
}
