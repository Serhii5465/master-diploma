package com.example.iotvandergraaf.presenter;

public class ApplicationModePresenter{

    public static enum MODE_APP{
        ADMIN_ARDUINO_EMULATE,
        ADMIN_ARDUINO_PHYSICAL,
        USER_ARDUINO_EMULATE,
        USER_ARDUINO_PHYSICAL
    }

    private MODE_APP currentMode;
    private String mode;
    private String typeUser;
    private static ApplicationModePresenter modePresenter;

    private ApplicationModePresenter(String mode, String typeUser) {
        this.mode = mode;
        this.typeUser = typeUser;
        setMode();
    }

    public static ApplicationModePresenter getInstance() {
        if(modePresenter == null){
            throw new AssertionError("You have to call init first");
        }
        return modePresenter;
    }

    public synchronized static ApplicationModePresenter init(String mode, String typeUser){
        if(modePresenter == null){
            modePresenter = new ApplicationModePresenter(mode,typeUser);
        }
        return modePresenter;
    }

    public MODE_APP getCurrentMode() {
        return currentMode;
    }

    public void setMode(){
        if(typeUser.equals("user") && mode.equals("Arduino ESP8266")){
            currentMode = MODE_APP.USER_ARDUINO_PHYSICAL;
        } else if(typeUser.equals("admin") && mode.equals("Arduino ESP8266")){
            currentMode = MODE_APP.ADMIN_ARDUINO_PHYSICAL;
        } else if (typeUser.equals("user") && mode.equals("Arduino Proteus")){
            currentMode = MODE_APP.USER_ARDUINO_EMULATE;
        } else if (typeUser.equals("admin") && mode.equals("Arduino Proteus")){
            currentMode = MODE_APP.ADMIN_ARDUINO_EMULATE;
        }
    }
}
