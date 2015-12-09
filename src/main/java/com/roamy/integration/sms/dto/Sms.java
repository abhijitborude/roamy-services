package com.roamy.integration.sms.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by Abhijit on 12/7/2015.
 */
@JacksonXmlRootElement(localName = "sms")
public class Sms {

    @JacksonXmlProperty(localName = "smsclientid")
    private String smsclientid;

    @JacksonXmlProperty(localName = "messageid")
    private String messageid;

    @JacksonXmlProperty(localName = "mobile-no")
    private String mobileno;

    public String getSmsclientid() {
        return smsclientid;
    }

    public void setSmsclientid(String smsclientid) {
        this.smsclientid = smsclientid;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "smsclientid='" + smsclientid + '\'' +
                ", messageid='" + messageid + '\'' +
                ", mobileno='" + mobileno + '\'' +
                '}';
    }
}
