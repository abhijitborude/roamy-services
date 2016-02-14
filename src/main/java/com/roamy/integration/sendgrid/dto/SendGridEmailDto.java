package com.roamy.integration.sendgrid.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 2/13/2016.
 */
public class SendGridEmailDto {

    private List<String> toAddresses = new ArrayList<>();
    private List<String> ccAddresses = new ArrayList<>();
    private List<String> bccAddresses = new ArrayList<>();

    private String fromAddress;
    private String fromName;

    private String replyToAddress;

    private String subject;
    private String html;

    private String category;

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public void addToAddress(String toAddress) {
        this.toAddresses.add(toAddress);
    }

    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(List<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public void addCcAddress(String ccAddress) {
        this.ccAddresses.add(ccAddress);
    }

    public List<String> getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(List<String> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public void addBccAddress(String bccAddress) {
        this.bccAddresses.add(bccAddress);
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getReplyToAddress() {
        return replyToAddress;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SendGridEmailDto{" +
                "toAddresses=" + toAddresses +
                ", ccAddresses=" + ccAddresses +
                ", bccAddresses=" + bccAddresses +
                ", subject='" + subject + '\'' +
                '}';
    }
}
