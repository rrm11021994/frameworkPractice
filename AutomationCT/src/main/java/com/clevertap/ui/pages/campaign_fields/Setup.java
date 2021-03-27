package com.clevertap.ui.pages.campaign_fields;

import java.util.Map;

public class Setup {

    private int push_android_ttl;
    private Map<String,String>  control_group;
    private Map<String,String>  change_control_group;

    public Map<String, String> getChange_control_group() {
        return change_control_group;
    }

    public void setChange_control_group(Map<String, String> change_control_group) {
        this.change_control_group = change_control_group;
    }



    public Map<String, String> getControl_group() {
        return control_group;
    }

    public void setControl_group(Map<String, String> control_group) {
        this.control_group = control_group;
    }

    public int getPush_android_ttl() {
        return push_android_ttl;
    }

    public void setPush_android_ttl(int push_android_ttl) {
        this.push_android_ttl = push_android_ttl;
    }
}
