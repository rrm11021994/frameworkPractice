package com.clevertap.ui.pages.campaign_fields;


public class Campaign {

    private String channel;
    private String type;
    private String csv_key;
    private When when;
    private Who who;
    private What what;
    private Setup setup;
    private String campaign_name;
    private String personalisation;
    private String currentRunningTestCaseName;

    public String getCurrentRunningTestCaseName() {
        return currentRunningTestCaseName;
    }

    public void setCurrentRunningTestCaseName(String currentRunningTestCaseName) {
        this.currentRunningTestCaseName = currentRunningTestCaseName;
    }

    public String getPersonalisation() {
        return personalisation;
    }

    public void setPersonalisation(String personalisation) {
        this.personalisation = personalisation;
    }

    public String getCsv_key() {
        return csv_key;
    }

    public void setCsv_key(String csv_key) {
        this.csv_key = csv_key;
    }

    public String getChannel() {
        return channel;
    }

    public String getType() {
        return type;
    }

    public When getWhen() {
        return when;
    }

    public Who getWho() {
        return who;
    }

    public What getWhat() {
        return what;
    }

    public Setup getSetup() {
        return setup;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWhen(When when) {
        this.when = when;
    }

    public void setWho(Who who) {
        this.who = who;
    }

    public void setWhat(What what) {
        this.what = what;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }
}
