import api.android.Android;
import core.ADB;
import core.MyLogger;
import core.UiObject;
import core.UiSelector;
import core.managers.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Level;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class Runner {

    public static void main(String args[]) throws MalformedURLException {

        MyLogger.log.setLevel(Level.DEBUG);
        DriverManager.createDriver();
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("platformName", "Android");
//        caps.setCapability("deviceName", "J9AAGF07X097CLB");
//        caps.setCapability("app", "C:\\Users\\AnujG\\Desktop\\APK Info_v1.2.11_apkpure.com.apk");
//
//
//        AndroidDriver driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"),caps);
//        Android.driver = driver;
//        ADB adb = new ADB("J9AAGF07X097CLB");
        //MyLogger.log.debug("Connrected Devices ::" +adb.getConnectedDevices());
        Android.adb.openAppActivity("org.zwanoo.android.speedtest","com.ookla.mobile4.screens.main.MainViewActivity");

        UiObject speedTestApp = new UiSelector().xpath("//android.widget.TextView[@text='Speedtest']").makeUiObject();
        UiObject goBtn = new UiSelector().resourceID("org.zwanoo.android.speedtest:id/go_button").makeUiObject();
        UiObject provider = new UiSelector().resourceID("org.zwanoo.android.speedtest:id/provider_item_title_text_view").makeUiObject();
        UiObject goBtnComplete = new UiSelector().resourceID("org.zwanoo.android.speedtest:id/suite_completed_go_button").makeUiObject();
        UiObject ping = new UiSelector().xpath("//android.widget.TextView[@text='Ping']//following::android.widget.TextView[1]").makeUiObject();
        UiObject downSpeed = new UiSelector().xpath("//android.widget.TextView[@text='DOWNLOAD']//following::android.widget.TextView[2]").makeUiObject();
        UiObject downSpeedUnits = new UiSelector().xpath("//android.widget.TextView[@text='DOWNLOAD']//following::android.widget.TextView[1]").makeUiObject();
        UiObject upSpeed = new UiSelector().xpath("//android.widget.TextView[@text='UPLOAD']//following::android.widget.TextView[2]").makeUiObject();
        UiObject upSpeedUnits = new UiSelector().xpath("//android.widget.TextView[@text='UPLOAD']//following::android.widget.TextView[1]").makeUiObject();

        Android.driver.pressKey(new KeyEvent(AndroidKey.HOME));
        speedTestApp.tap();
        goBtn.waitToAppear(5);
        MyLogger.log.info("Internet Service Provider :: "+provider.getText());
        goBtn.tap();
        goBtnComplete.waitToAppear(180);
        MyLogger.log.info("Ping :: "+ping.getText());
        MyLogger.log.info("Download Speed :: "+downSpeed.getText()+ " "+ downSpeedUnits.getText());
        MyLogger.log.info("Upload Speed :: "+upSpeed.getText()+ " "+ upSpeedUnits.getText());

        DriverManager.killDriver();

    }
}
