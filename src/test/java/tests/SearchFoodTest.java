package tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.OnboaringPage;
import com.qa.pages.SearchFoodPage;
import com.qa.pages.SelectLocationPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

public class SearchFoodTest extends BaseTest {
    OnboaringPage onboaringPage;
    LoginPage loginPage;
    SelectLocationPage selectLocationPage;
    JSONObject loginUsers;
    SearchFoodPage searchFoodPage;

    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais  = getClass().getClassLoader().getResourceAsStream(dataFileName);
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
        onboaringPage.OnboaringAllowNotification();
        loginPage.beforeLogin();
        loginPage.login(loginUsers.getJSONObject("validUser").getString("phone"),
                loginUsers.getJSONObject("validUser").getString("password"));
        selectLocationPage.SelectLocation(loginUsers.getJSONObject("validAddress").getString("address"));
    }
    @AfterMethod
    public void afterMethod() {
        closeApp();
        launchApp();
    }

    @Test
    public void invalidSearchFood() {
        searchFoodPage.SearchFood(loginUsers.getJSONObject("invalidNameFood").getString("nameFood"));
        Assert.assertTrue(false);
    }

    @Test
    public void SearchFoodSuccessful() {
        searchFoodPage.SearchFood(loginUsers.getJSONObject("validNameFood").getString("nameFood"));
        Assert.assertTrue(true);
    }
}
