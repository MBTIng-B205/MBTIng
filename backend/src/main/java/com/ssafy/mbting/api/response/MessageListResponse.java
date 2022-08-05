package com.ssafy.mbting.api.response;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageListResponse {
    @Builder.Default
    List<MessageResponse> messages = new ArrayList<>();
    PageRequest pageable;
}
