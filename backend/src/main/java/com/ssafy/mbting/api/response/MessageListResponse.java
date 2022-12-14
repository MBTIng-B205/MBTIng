package com.ssafy.mbting.api.response;

import com.ssafy.mbting.common.util.PagingResponse;
import lombok.*;
import org.springframework.data.domain.PageRequest;

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
    PagingResponse pagingResponse;
}
