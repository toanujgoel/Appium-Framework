package core;

import core.managers.ServerManager;

import java.util.ArrayList;

public class ADB {
    private String ID;

    public ADB(String deviceID){
        ID=deviceID;
    }

    public static String command(String command){
        MyLogger.log.debug("Formatting command - "+command);
        if (command.startsWith("adb "))
            command.replace("adb ", ServerManager.getAndroidHome()+"/platform-tools/adb ");
        else throw new RuntimeException("This method is designed to run adb commands only");

        MyLogger.log.debug("Formatted command - "+command);
        String output = ServerManager.runCommand(command);

        MyLogger.log.debug("Output for the command is - "+output);
        if (output==null) output="";
        return output.trim();

    }

    public static void killServer(){
        command("adb kill-server");
    }

    public static void startServer(){
        command("adb start-server");
    }

    public static ArrayList getConnectedDevices(){
        ArrayList<String> devices = new ArrayList();
        String output = command("adb devices");
        for(String line : output.split("\n")){
            if (line.endsWith("device"))
                devices.add(line.replace("device","").trim());
        }
        return devices;
    }

    public String getForegroundActivity(){
        return command("adb -s "+ID+" shell dumpsys window windows | grep mCurrentFocus");
    }

    public String getAndroidVersionAsString(){
        String output = command("adb -s "+ID+" shell getprop ro.build.version.release");
        if(output.length() == 3) output+=".0";
        return output;
    }

    public int getAndroidVersion(){
        return Integer.parseInt(getAndroidVersionAsString().replaceAll("\\.",""));
    }

    public ArrayList getInstalledPackages(){
        ArrayList packages = new ArrayList();
        String output = command("adb devices -s "+ID+" shell pm list packages");
        for(String packageID : output.split("\n")){
            packages.add(packageID.replace("package:","").trim());
        }
        return packages;
    }

    public void openAppActivity(String packageID, String activityID){
        command("adb -s "+ID+" shell am start -c api.android.intent.category.LAUNCHER -a api.android.intent.action.MAIN -n "+packageID+"/"+activityID);
    }

    public void clearAppData(String packageID){
        command("adb -s "+ID+" shell pm clear "+packageID);
    }

    public void forceStopApp(String packageID){
        command("adb -s "+ID+" shell am force-stop "+packageID);
    }

    public void installApp(String apkPath){
        command("adb -s "+ID+" install "+apkPath);
    }

    public void uninstallApp(String packageID){
        command("adb -s "+ID+" uninstall "+packageID);
    }

    public void clearLogBuffer(){
        command("adb -s "+ID+" shell -c");
    }

    public void pushFile(String source, String target){
        command("adb -s "+ID+" push "+source+" "+target);
    }

    public void pullFile(String source, String target){
        command("adb -s "+ID+" pull "+source+" "+target);
    }

    public void deleteFile(String target){
        command("adb -s "+ID+" shell rm "+target);
    }

    public void moveFile(String source, String target){
        command("adb -s "+ID+" shell mv "+source+" "+target);
    }

    public void takeScreenshot(String target){
        command("adb -s "+ID+" shell screencap "+target);
    }

    public void rebootDevice(){
        command("adb -s "+ID+" reboot");
    }

    public String getDeviceModel(){
        return command("adb -s "+ID+" shell getprop ro.product.model");
    }

    public String getDeviceSerialNumber(){
        return command("adb -s "+ID+" shell getprop ro.serialno");
    }

    public String getDeviceCarrier(){
        return command("adb -s "+ID+" shell getprop gsm.operator.alpha");
    }


    public static void main(String args[]){
        System.out.println(command("adb -s J9AAGF07X097CLB shell top -n 1 | grep -i 'logcat'"));
    }
}

