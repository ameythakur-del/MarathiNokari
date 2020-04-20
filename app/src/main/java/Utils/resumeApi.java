package Utils;

import android.app.Application;

public class resumeApi extends Application{
    private String Name;
    private String UserId;
    private static resumeApi instance;

    public static resumeApi getInstance(){
        if(instance == null)
            instance = new resumeApi();
        return instance;
    }

    public resumeApi(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
