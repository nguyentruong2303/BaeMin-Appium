package tests;

import com.qa.base.BaseTest;
import com.qa.pages.OnboaringPage;
import com.qa.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnboaringTest extends BaseTest {
    TestUtils utils;
    OnboaringPage onboaringPage;

    @Test
    public void OnboaringTest() {
        onboaringPage = new OnboaringPage();
        onboaringPage.OnboaringMore();
        Assert.assertTrue(true);
    }
}
