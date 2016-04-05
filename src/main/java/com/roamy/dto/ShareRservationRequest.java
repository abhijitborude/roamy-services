package com.roamy.dto;

import java.util.List;

/**
 * Created by Abhijit on 4/4/2016.
 */
public class ShareRservationRequest {

    private List<FriendInfo> friends;

    public List<FriendInfo> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendInfo> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "ShareRservationRequest{" +
                "friends=" + friends +
                '}';
    }
}
