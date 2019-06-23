package core;

import api.android.Android;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class UiObject {
    private String locator;

    UiObject(String locator){
        this.locator=locator;
        MyLogger.log.debug("UiObject Locator ::"+ this.locator);
    }

    public Boolean isXpath(){
        return !locator.contains("Ui Selector");
    }

    public Boolean exists(){
        try {
            WebElement element;
            if (isXpath()) element= Android.driver.findElementByXPath(locator);
            else element= Android.driver.findElementByAndroidUIAutomator(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean isChecked(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("checked").equals("true");
        }


    public Boolean isCheckable(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("checkable").equals("true");
    }

    public Boolean isClickable(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("clickable").equals("true");
    }


    public Boolean isEnabled(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("enabled").equals("true");
    }

    public Boolean isFocusable(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("focusable").equals("true");
    }

    public Boolean isFocused(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("focused").equals("true");
    }

    public Boolean isScrollable(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("scrollable").equals("true");
    }

    public Boolean isLongClickable(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("longClickable").equals("true");
    }

    public Point getLocation(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getLocation();
    }

    public String getText(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("name");
    }

    public String getResourceId(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("resourceId");
    }

    public String getClassName(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("className");
    }

    public String getContentDesc(){
        WebElement element;
        if (isXpath()) element= Android.driver.findElementByXPath(locator);
        else element= Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("contentDesc");
    }

    public UiObject clearText(){
        if (isXpath()) Android.driver.findElementByXPath(locator).clear();
        else Android.driver.findElementByAndroidUIAutomator(locator).clear();
        return this;
    }

    public UiObject typeText(String value){
        if (isXpath()) Android.driver.findElementByXPath(locator).sendKeys(value);
        else Android.driver.findElementByAndroidUIAutomator(locator).sendKeys(value);
        return this;
    }

    public UiObject tap(){
        if (isXpath()) Android.driver.findElementByXPath(locator).click();
        else Android.driver.findElementByAndroidUIAutomator(locator).click();
        return this;
    }

    public UiObject waitToAppear(int wait){
        Timer timer = new Timer();
        timer.start();
        while(!timer.isExpired(wait)) if (exists()) break;
        if(timer.isExpired(wait) && !exists())
            throw new AssertionError("Element: "+locator+" failed to appear within- "+wait+" secconds");
        return this;
    }

    public UiObject waitToDisappear(int wait){
        Timer timer = new Timer();
        timer.start();
        while(!timer.isExpired(wait)) if (!exists()) break;
        if(timer.isExpired(wait) && exists())
            throw new AssertionError("Element: "+locator+" failed to disappear within- "+wait+" secconds");
        return this;
    }
}
