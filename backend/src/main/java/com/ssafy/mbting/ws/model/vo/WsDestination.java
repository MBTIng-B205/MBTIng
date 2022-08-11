package com.ssafy.mbting.ws.model.vo;

public class WsDestination {

    private static final String DESTINATION_PREFIX = "/ws/sub";

    private final String email;

    private WsDestination(String email) {
        this.email = email;
    }

    public static WsDestination of(String email) {
        return new WsDestination(email);
    }

    public String getDestination() {
        return DESTINATION_PREFIX + "/indi/" + email;
    }
}
