package com.ssafy.mbting.common.util;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.Map;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageNavigation {
    int page;
    int size;
    SearchUtil searchUtil;
}
