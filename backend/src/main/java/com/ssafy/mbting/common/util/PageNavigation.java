package com.ssafy.mbting.common.util;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageNavigation {

    Integer page;
    Integer size;
    SearchUtil searchUtil;
}
