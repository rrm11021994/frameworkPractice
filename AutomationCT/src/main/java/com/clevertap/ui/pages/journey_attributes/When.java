package com.clevertap.ui.pages.journey_attributes;

public class When {

    private String journeyStartTime;
    private String journeyEndTime;
    private String journeyConversionTime;
    private String allowUsersToReenter;
    private String customControlGroup;
    private String systemControlGroup;

    public String getJourneyStartTime() {
        return journeyStartTime;
    }

    public void setJourneyStartTime(String journeyStartTime) {
        this.journeyStartTime = journeyStartTime;
    }

    public String getJourneyEndTime() {
        return journeyEndTime;
    }

    public void setJourneyEndTime(String journeyEndTime) {
        this.journeyEndTime = journeyEndTime;
    }

    public String getJourneyConversionTime() {
        return journeyConversionTime;
    }

    public void setJourneyConversionTime(String journeyConversionTime) {
        this.journeyConversionTime = journeyConversionTime;
    }

    public String getAllowUsersToReenter() {
        return allowUsersToReenter;
    }

    public void setAllowUsersToReenter(String allowUsersToReenter) {
        this.allowUsersToReenter = allowUsersToReenter;
    }

    public String getCustomControlGroup() {
        return customControlGroup;
    }

    public void setCustomControlGroup(String customControlGroup) {
        this.customControlGroup = customControlGroup;
    }

    public String getSystemControlGroup() {
        return systemControlGroup;
    }

    public void setSystemControlGroup(String systemControlGroup) {
        this.systemControlGroup = systemControlGroup;
    }
}
