package com.ssafy.mbting.ws.model.vo;

import com.ssafy.mbting.config.StompConfig;

public class IndividualDestination {

    private final String email;

    private IndividualDestination(String email) {
        this.email = email;
    }

    public static IndividualDestination of(String email) {
        return new IndividualDestination(email);
    }

    @Override
    public String toString() {
        return StompConfig.BROKER_DESTINATION_PREFIX + "/indi/" + email;
    }
}
