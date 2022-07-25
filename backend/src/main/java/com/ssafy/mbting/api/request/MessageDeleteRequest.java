package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDeleteRequest {
    public enum DeletedBy {
        SENDER, RECEIVER
    }
    private DeletedBy deletedBy;
}
