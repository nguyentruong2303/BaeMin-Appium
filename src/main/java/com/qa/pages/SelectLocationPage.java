package com.qa.pages;

import com.qa.base.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SelectLocationPage extends BaseTest {
    TestUtils utils = new TestUtils();

//    @AndroidFindBy(id = "com.woowahan.vn.baemin.dev:id/com_appboy_inappmessage_modal_button_layout_single")
//    private MobileElement btnGoWork;

    @AndroidFindBy(accessibility = "home_nav_address")
    private MobileElement btnLocation;

    @AndroidFindBy(accessibility = "address_search_keyword")
    private MobileElement addressTxt;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup")
    private MobileElement selectAddress;

    @AndroidFindBy(accessibility = "address_confirm")
    private MobileElement btnConfirm;

    public void SelectLocation(String address) {
//        click(btnGoWork);
        click(btnLocation);
        sendKeys(addressTxt,address);
        click(selectAddress);
        click(btnConfirm);

    }

}
