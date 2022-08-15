package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.ws.service.MeetingMatchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MeetingMatchService meetingMatchService;

    @GetMapping("/match/inprogress/{inprogress}")
    public void register(@PathVariable("inprogress") Boolean inProgress) {
        meetingMatchService.setInProgress(inProgress);
    }
}
