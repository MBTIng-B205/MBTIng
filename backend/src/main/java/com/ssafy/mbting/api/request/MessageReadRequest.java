package com.ssafy.mbting.api.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadRequest {
    private List<Long> readList= new ArrayList<>();
}
