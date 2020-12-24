package tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.OnboaringPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class LoginTest extends BaseTest {
    OnboaringPage onboaringPage;
    JSONObject loginUsers;
    TestUtils utils;
    LoginPage loginPage;

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
    public void afterClass() {

    }
    @BeforeMethod
    public void beforeMethod(Method method) {
        //utils.log().info("Starting test " + method.getName() );
        loginPage = new LoginPage();
        onboaringPage = new OnboaringPage();
        onboaringPage.OnboaringAllowNotification();
        loginPage.beforeLogin();


    }
    @AfterMethod
    public void afterMethod() {
        closeApp();
        launchApp();
    }
    @Test(priority = 0)
    public void invalidPhone() {

        loginPage.login(loginUsers.getJSONObject("invalidPhone").getString("phone")
                ,"");
        Assert.assertTrue(false);
    }
    @Test(priority = 1)
    public void invalidPassword() {
        loginPage.login(loginUsers.getJSONObject("invalidPassword").getString("phone"),
                loginUsers.getJSONObject("invalidPassword").getString("password"));
        Assert.assertTrue(false);
    }
    @Test(priority = 2)
    public void successfulLogin() {
        loginPage.login(loginUsers.getJSONObject("validUser").getString("phone"),
                loginUsers.getJSONObject("validUser").getString("password"));
        Assert.assertTrue(true);
    }

}
