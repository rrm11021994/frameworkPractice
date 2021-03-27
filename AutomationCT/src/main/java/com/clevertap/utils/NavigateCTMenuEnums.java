package com.clevertap.utils;

public class NavigateCTMenuEnums {

    public enum Menu {

        BOARDS("Boards"),
        SEGMENT("Segment"),
        ANALYZE("Analyze"),
        ENGAGE("Engage"),
        REAL_IMPACT("Real Impact"),
        SETTINGS("Settings"),
        AB_TEST("A/B Tests");

        private String text;

        Menu(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

    public enum Submenu {

        All_BOARDS("All Boards"),
        TODAY("Today"),
        MOBILE_APP("Mobile App"),
        Unistall("Uninstall"),
        REVENUE("Revenue"),

        FIND_PEOPLE("Find People"),
        SEGMENTS("Segments"),
        RFM("RFM"),

        EVENTS("Events"),
        FUNNELS("Funnels"),
        COHORTS("Cohorts"),
        TRENDS("Trends"),
        PIVOTS("Pivots"),
        FLOWS("Flows"),
        ATTRIBUTION("Attribution"),
        DEVICE_CROSSOVERS("Device Crossovers"),

        CAMPAIGNS("Campaigns"),
        JOURNEYS("Journeys"),
        CLEVER_CAMPAIGNS("Clever Campaigns"),
        CONTROL_GROUPS("Control Groups"),
        CATALOGS("Catalogs"),
        RECOMMENDATIONS("Recommendations"),
        CONVERSATOINS("Conversations"),

        DYNAMIC_VARIABLES("Dynamic Variables"),
        VISUAL_EDITOR("Visual Editor"),

        ACCOUNT("Account"),
        APPS_AND_USAGE("Apps & Usage"),
        MYPROFILE("My Profile"),
        USERS("Users"),
        ROLES("Roles"),
        BILLING("Billing"),
        EVENTS_USER_PROPERTIS("Events & User properties"),
        MY_DOWNLOADS("My Downloads"),
        DATA_EXPORTS("Data Exports"),
        PARTNER_DATA_EXPORTS("Partner Data Exports"),
        CSV_UPLOADS("CSV Uploads"),
        EXTERNAL_USER_SEGMENT("External User Segment"),
        INTEGRATION("Integration"),
        CAMPAIGN_LABELS("Campaign Label"),
        CAMPAIGN_REPORTS("Campaign Reports"),
        LABELS("Labels"),
        REPORTS("Reports"),
        CAMPAIGN_SETTINGS("Campaign Settings"),
        APP_UNINSTALLS("App uninstalls"),
        NOSUBMENU("");

        private String text;

        Submenu(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }



    public enum SuperSubMenu {

        PUSH_NOTIFICATIONS("Push Notifications"),
        EMAIL("Email"), SMS("SMS"),
        WEBPUSH("Web push"),
        WHATSAPP("WhatsApp"),
        REMARKETING("Remarketing"),
        WEBHOOKS("Webhooks"),
        GLOBAL_CAMPAIGN_LIMITS("Global campaign limits"),
        BEST_TIME_CAMPAIGN("Best time campaign"),
        PUSH_AMPLIFICATION("Push amplification"),

        NOSUPERSUBMENU("");
        private String text;

        SuperSubMenu(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

}
