package com.clevertap.utils;

public class DashboardEnums {

    public enum CampaignTargetType {
        ONETIME("scheduled"),
        MULTIPLEDATES("scheduled recurring"),
        RECURRING("scheduled recurring"),
        SINGLEACTION("instant event triggered"),
        INACTIONWITHINTIMEFORLESSTHAN24HOURS("instant event closure"),
        INACTIONWITHINTIMEFORMORETHAN24HOURS("scheduled event closure"),
        ONADATETIME("property time trigger");


        private String targetName;

        CampaignTargetType(String targetName) {
            this.targetName = targetName;
        }

        public String toString() {
            return targetName;
        }
    }

    public enum CampaignTargetTypeSelection {
        INSTANTEVENTTRIGGERED("0"),
        SCHEDULED("1"),
        INSTANTEVENTCLOSURE("2"),
        SCHEDULEDEVENTCLOSURE("3"),
        RECURRENT("4"),
        POSTACTION("5"),
        PROPERTYTIMETRIGGERED("6"),
        TRIGGEREDFROMAPIUSINGIDENTITY("7"),
        SCHEDULEDEVENTTRIGGERED("8"),
        SCHEDULEDRECURRING("9"),
        SCHEDULEDJOURNEYSTEPTRIGGERED("10"),
        JOURNEYENTRYTRIGGERED("11");

        private String targetTypeValue;

        CampaignTargetTypeSelection(String targetTypeValue) {
            this.targetTypeValue = targetTypeValue;
        }

        public String toString() {
            return targetTypeValue;
        }
    }

    /*run evMaster in browser console to get the list of events key and value*/
    public enum CampaignEvents {

        UTMVISITED(1),
        APPLAUNCHED(2),
        NOTIFICATIONVIEWED(3),
        APPUNINSTALLED(4),
        CHARGED(5),
        APPINSTALLED(6),
        NOTIFICATIONCLICKED(7),
        PUSHIMPRESSIONS(8),
        NOTIFICATIONDELIVERED(9),
        NOTIFICATIONREPLIED(10),
        REPLYSENT(11),
        IDENTITYSET(12),
        IDENTITYERROR(13),
        IDENTITYRESET(14),
        PRODUCTVIEWED(15),
        ADDEDTOCART(16),
        HALFINTERSTITIAL(18),
        COVER(19),
        INTERSTITIAL(20),
        HEADER(21),
        INTERSTITIALVIDEO(22),
        FOOTER(23),
        COVERIMAGE(24),
        TABLETONLYHEADER(25),
        INTERSTITIALGIF(26),
        INTERSTITIALIOS(27),
        INTERSTITIALIMAGE(28),
        HALFINTERSTITIALIMAGE(29),
        NOTIFICATIONSENT(-7);


        private int eventKey;

        CampaignEvents(int eventKey) {
            this.eventKey = eventKey;
        }

        public int toInt() {
            return eventKey;
        }

    }

    public enum DeviceOS {
        ANDROID("1"),
        IOS("2");

        private String deviceOs;

        DeviceOS(String deviceOs) {
            this.deviceOs = deviceOs;
        }

        public String toString() {
            return deviceOs;
        }
    }

    public enum DeviceName {
        DESKTOP("0"),
        MOBILE("1"),
        TABLET("2"),
        TV("3");

        private String deviceName;

        DeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String toString() {
            return deviceName;
        }
    }

    public enum EventStoreOperator {

        EQ(1),
        GT(2),
        GE(3),
        LT(4),
        LE(5),
        BW(6),
        CO(7),
        NEQ(8),
        BWE(9),
        UNKNOWN(-1),
        SET(10),
        NSET(11),
        NCO(12),
        EQR(13),
        NEQR(14);

        private int esOpetaorKey;

        EventStoreOperator(int esOpetaorKey) {
            this.esOpetaorKey = esOpetaorKey;
        }

        public int toInt() {
            return esOpetaorKey;
        }


    }

    public enum EmailFormData {
        sendgrid("sendgrid", "sendgrid email", "smpt.sendgrid.net", "BenSequeira", "Clevertap@123", "ben.sequeira@clevertap.com");

        private String serviceProvider;
        private String nickName;
        private String host;
        private String userName;
        private String pwd;
        private String fromAddress;

        EmailFormData(String serviceProvider, String nickName, String host, String userName, String pwd, String fromAddress) {
            this.serviceProvider = serviceProvider;
            this.nickName = nickName;
            this.host = host;
            this.userName = userName;
            this.pwd = pwd;
            this.fromAddress = fromAddress;
        }

        public String getServiceProvider() {
            return serviceProvider;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public String getHost() {
            return host;
        }

        public String getNickName() {
            return nickName;
        }

        public String getPwd() {
            return pwd;
        }

        public String getUserName() {
            return userName;
        }
    }
    public enum campaignCrudOperation {

        RUNNING("View report", "Edit", "Clone", "Stop"),
        STOPPED("View report", "", "Clone", ""),
        AWAITNEXTRUN("View report", "Edit", "Clone", "Stop"),
        COMPLETED("View report", "", "Clone", ""),
        DRAFT("", "Edit", "Clone", ""),
        SCHEDULED("View report", "Edit", "Clone", "Stop"),
        APPROVALPENDING("", "", "Clone", "");

        private String viewReport;
        private String edit;
        private String clone;
        private String stop;

        campaignCrudOperation(String viewReport, String edit, String clone, String stop) {
            this.viewReport = viewReport;
            this.edit = edit;
            this.clone = clone;
            this.stop = stop;
        }

        public String getViewReportStatus() {
            return viewReport;
        }

        public String getEditStatus() {
            return edit;
        }

        public String getCloneStatus() {
            return clone;
        }

        public String getStopStatus() {
            return stop;
        }

    }

    public enum SystemEventProperties{

        CTSDVVERSION("CT SDK Version"), CTOSVERSION("CT OS Version"), CTAPPVERSION("CT App Version"),
        CTNETWORKCARRIES("CT Network Carrier"), CTSOURCE("CT Source"), CTLATITUDE("CT Longitude"), CTLONGITUDE("CT Latitude");

        private String event;
        SystemEventProperties(String event) {
            this.event = event;
        }
        public String getEvent(){
            return event;
        }

        public static boolean contains(String eventProp){
            for(SystemEventProperties e : SystemEventProperties.values()){
                return e.event.equalsIgnoreCase(eventProp) ? true : false;
            }
            return false;
        }
    }
}
