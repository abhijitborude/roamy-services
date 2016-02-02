package com.roamy.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;

import java.util.*;

/**
 * Created by Abhijit on 11/12/2015.
 */
public class RestResponse {

    private Object data;
    private int status;

    private Map<String, String> messages;
    private Map<String, String> _links;

    @JsonSerialize(using = CustomDateSerializer.class)
    private final Date _responseCurrentAt = Calendar.getInstance().getTime();

    private final UUID _correlationId = UUID.randomUUID();

    public RestResponse(Object data, int status) {
        this.data = data;
        this.status = status;
        messages = new HashMap<>();
        _links = new HashMap<>();
    }

    public RestResponse(Object data, int status, Map<String, String> messages, Map<String, String> links) {
        this.data = data;
        this.status = status;
        if (messages != null) {
            this.messages = messages;
        } else {
            this.messages = new HashMap<>();
        }
        if (links != null) {
            this._links = links;
        } else {
            this._links = new HashMap<>();
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> links) {
        this._links = links;
    }

    public Date get_responseCurrentAt() {
        return _responseCurrentAt;
    }

    public UUID get_correlationId() {
        return _correlationId;
    }
}
