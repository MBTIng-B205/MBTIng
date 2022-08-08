package com.ssafy.mbting.api.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadRequest {
    @Builder.Default
    private List<Long> readList= new ArrayList<>();
}