package com.ssafy.mbting.common.util;


import lombok.*;
import org.springframework.data.domain.Pageable;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse {
    Pageable pageable;
    Long totalcount;
    Integer totalpage;
}
