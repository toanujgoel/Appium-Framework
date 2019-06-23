package core.managers;

import api.android.Android;
import core.ADB;
import core.MyLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DriverManager {

    private static HashMap<String,URL> hosts;
    private static String unlockApkPackage = "com.package";

    private static DesiredCapabilities getCaps(String deviceId){
        MyLogger.log.info("Creating driver caps for device :: "+deviceId);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", deviceId);
        caps.setCapability("app", "C:\\Users\\AnujG\\Desktop\\APK Info_v1.2.11_apkpure.com.apk");
        return caps;
    }

    private static ArrayList<String> getAvailableDevices(){
        ArrayList<String> connectedDevices = ADB.getConnectedDevices();
        MyLogger.log.info("Checking for available devices in these connected devices :: "+connectedDevices);
        ArrayList availableDevices = new ArrayList();
        for(String connectedDevice: connectedDevices){
            if(new ADB(connectedDevice).getInstalledPackages().contains(unlockApkPackage)) throw new RuntimeException("No device available for testing");
            else availableDevices.add(connectedDevice);
        }return availableDevices;
    }

    private static URL getHosts(String deviceId) throws MalformedURLException{
        if (hosts == null) {
            hosts = new HashMap<>();
            hosts.put(deviceId, new URL("http://localhost:4723/wd/hub"));
        }return hosts.get(deviceId);
    }


    public static void createDriver() throws MalformedURLException {
        MyLogger.log.info("Trying to create new driver");
        for(String deviceId: getAvailableDevices())
            try {
                Android.driver = new AndroidDriver(getHosts(deviceId), getCaps(deviceId));
                ADB adb = new ADB(deviceId);
            }catch(Exception e){
                MyLogger.log.info("There seems some exception connecting to current device. Ignore and try to connect other device");
                e.printStackTrace();
            }
    }

    public static void killDriver(){
        if(Android.driver!=null){
            MyLogger.log.info("Killing driver...");
            Android.driver.quit();
            Android.adb.uninstallApp(unlockApkPackage);
        }else MyLogger.log.info("Driver is not initialized. Nothing to kill...");
    }
}
