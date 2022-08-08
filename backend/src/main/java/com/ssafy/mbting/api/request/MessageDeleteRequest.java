package com.ssafy.mbting.api.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDeleteRequest {

    public enum DeletedBy {
        SENDER, RECEIVER
    }

    private DeletedBy deletedBy;
    @Builder.Default
    private List<Long> deletelist = new ArrayList<>();
}
