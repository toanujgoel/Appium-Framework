package core;

public class UiSelector {

    private String locator = "new UiSelector()";

    public UiSelector resourceID(String value){
        locator+=".resourcceID(\""+value+"\")";
        return this;
    }

    public UiSelector text(String value){
        locator+=".text(\""+value+"\")";
        return this;
    }

    public UiSelector textContains(String value){
        locator+=".textContains(\""+value+"\")";
        return this;
    }

    public UiSelector description(String value){
        locator+=".description(\""+value+"\")";
        return this;
    }

    public UiSelector descriptionContains(String value){
        locator+=".descriptionContains(\""+value+"\")";
        return this;
    }
    public UiSelector descriptionMatches(String regex){
        locator+=".descriptionMatches(\""+regex+"\")";
        return this;
    }

    public UiSelector checked(Boolean value){
        locator+=".checked(\""+value+"\")";
        return this;
    }
    public UiSelector enabled(Boolean value){
        locator+=".enabled(\""+value+"\")";
        return this;
    }
    public UiSelector clickable(Boolean value){
        locator+=".clickable(\""+value+"\")";
        return this;
    }
    public UiSelector className(String value){
        locator+=".className(\""+value+"\")";
        return this;
    }
    public UiSelector xpath(String xpath){
        locator = xpath;
        return this;
    }

    public UiObject makeUiObject(){
        return new UiObject(locator);

    }
}
