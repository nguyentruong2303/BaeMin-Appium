package com.qa.pages;

import com.qa.base.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class OnboaringPage extends BaseTest {
    TestUtils utils = new TestUtils();

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.Button[2]/android.widget.TextView")
    private MobileElement btnExploreMore;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.Button[2]/android.widget.TextView")
    private MobileElement btnNext;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.Button[2]/android.widget.TextView")
    private MobileElement btnPermission;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
    private MobileElement btnDenyNotification;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    private MobileElement btnAllowNotification;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.Button[1]/android.widget.TextView")
    private MobileElement btnExploreMoreNow;

    @AndroidFindBy(accessibility = "Go work")
    private MobileElement btnGoWork;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[1]")
    private MobileElement titleOnboaring;

    @AndroidFindBy(id = "android:id/button2")
    private MobileElement btnCancelLocation;

    public void OnboaringAllowNotification() {
        click(btnExploreMore);
        click(btnNext);
        click(btnPermission);
        click(btnAllowNotification);
    }
    public void OnboaringDenyNotification() {
        click(btnExploreMore,"Click button Explore More");
        click(btnNext,"Click button next");
        click(btnPermission,"click button permission");
        click(btnDenyNotification,"Click button deny notification");
    }
    public void OnboaringMore() {
        OnboaringAllowNotification();
        click(btnExploreMoreNow,"click button explore more now");
        //click(btnGoWork,"Title Go Work in OnBoaringMore");
    }
    public void OnboaringMoreDenyNotification() {
        OnboaringDenyNotification();
        click(btnExploreMoreNow,"click button explore more now");
        click(btnCancelLocation,"click button Cancel location");
    }
}
