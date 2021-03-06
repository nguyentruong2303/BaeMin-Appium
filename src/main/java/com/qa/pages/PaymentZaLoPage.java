package com.qa.pages;

import com.qa.base.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PaymentZaLoPage extends BaseTest {
    TestUtils utils = new TestUtils();

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"merchant_dish_name\"])[1]")
    private MobileElement chooseFood;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"merchant_dishmodal_quantity_increase\"]/android.view.ViewGroup")
    private MobileElement qualityTxt;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"merchant_dishmodal_add_text\"]")
    private MobileElement btnAdd;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView[1]")
    private MobileElement btnCart;

    @AndroidFindBy(accessibility = "basket_coupon_use")
    private MobileElement selectCoupons;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup")
    private MobileElement btnCash;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"payment_zalo_button\"]/android.view.ViewGroup")
    private MobileElement btnZaLo;

    @AndroidFindBy(xpath = "")
    private MobileElement btnConfirm;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"basket_order_button\"]")
    private MobileElement btnOrder;

    public void PaymentZaLo() {
        click(chooseFood);
        click(qualityTxt);
        click(btnAdd);
        click(btnCart);
        scrollToElement();
        click(selectCoupons);
        click(btnCash);
        click(btnZaLo);
        click(btnConfirm);
        click(btnOrder);
    }
}
