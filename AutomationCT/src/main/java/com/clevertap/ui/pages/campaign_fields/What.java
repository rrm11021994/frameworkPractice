package com.clevertap.ui.pages.campaign_fields;

import java.util.Map;

public class What {

    private String sms_message;
    private String push_title;
    private String push_message;
    private String push_androido_channel;
    private int email_template_number;
    private String email_from_name;
    private String email_subject;
    private String inapp_title;
    private String select_message_type;
    private String select_template_type;
    private String template_button_type;
    private Map<String, String> ios;
    private Map<String, String> android;
    private String change_inapp_templateType;
    private String change_inapp_messageType;
    private String change_inapp_templateButton;
    private String whatsAppMessageToBeSent;
    private String message_to_be_selected;
    private String include_Msg_In_AppInbox;

    public String getInclude_Msg_In_AppInbox() {
        return include_Msg_In_AppInbox;
    }

    public void setInclude_Msg_In_AppInbox(String include_Msg_In_AppInbox) {
        this.include_Msg_In_AppInbox = include_Msg_In_AppInbox;
    }

    public String getMessage_to_be_selected() {
        return message_to_be_selected;
    }

    public void setMessage_to_be_selected(String message_to_be_selected) {
        this.message_to_be_selected = message_to_be_selected;
    }

    public String getWhatsAppMessageToBeSent() {
        return whatsAppMessageToBeSent;
    }

    public void setWhatsAppMessageToBeSent(String whatsAppMessageToBeSent) {
        this.whatsAppMessageToBeSent = whatsAppMessageToBeSent;
    }

    public String getChange_inapp_templateButton() {
        return change_inapp_templateButton;
    }

    public void setChange_inapp_templateButton(String change_inapp_templateButton) {
        this.change_inapp_templateButton = change_inapp_templateButton;
    }

    public String getChange_inapp_messageType() {
        return change_inapp_messageType;
    }

    public void setChange_inapp_messageType(String change_inapp_messageType) {
        this.change_inapp_messageType = change_inapp_messageType;
    }

    public String getChange_inapp_templateType() {
        return change_inapp_templateType;
    }

    public void setChange_inapp_templateType(String change_inapp_templateType) {
        this.change_inapp_templateType = change_inapp_templateType;
    }

    public Map<String, String> getAndroid() {
        return android;
    }

    public void setAndroid(Map<String, String> android) {
        this.android = android;
    }

    public Map<String, String> getIos() {
        return ios;
    }

    public void setIos(Map<String, String> ios) {
        this.ios = ios;
    }

    public String getTemplate_button_type() {
        return template_button_type;
    }

    public void setTemplate_button_type(String template_button_type) {
        this.template_button_type = template_button_type;
    }

    public String getSelect_template_type() {
        return select_template_type;
    }

    public void setSelect_template_type(String select_template_type) {
        this.select_template_type = select_template_type;
    }

    public String getSelect_message_type() {
        return select_message_type;
    }

    public void setSelect_message_type(String select_message_type) {
        this.select_message_type = select_message_type;
    }

    public String getSms_message() {
        return sms_message;
    }

    public String getPush_title() {
        return push_title;
    }

    public String getPush_message() {
        return push_message;
    }

    public String getPush_androido_channel() {
        return push_androido_channel;
    }

    public int getEmail_template_number() {
        return email_template_number;
    }

    public String getEmail_from_name() {
        return email_from_name;
    }

    public String getEmail_subject() {
        return email_subject;
    }

    public String getInapp_title() {
        return inapp_title;
    }

    public void setSms_message(String sms_message) {
        this.sms_message = sms_message;
    }

    public void setPush_title(String push_title) {
        this.push_title = push_title;
    }

    public void setPush_message(String push_message) {
        this.push_message = push_message;
    }

    public void setPush_androido_channel(String push_androido_channel) {
        this.push_androido_channel = push_androido_channel;
    }

    public void setEmail_template_number(int email_template_number) {
        this.email_template_number = email_template_number;
    }

    public void setEmail_from_name(String email_from_name) {
        this.email_from_name = email_from_name;
    }

    public void setEmail_subject(String email_subject) {
        this.email_subject = email_subject;
    }

    public void setInapp_title(String inapp_title) {
        this.inapp_title = inapp_title;
    }
}
