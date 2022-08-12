package com.ssafy.mbting.ws.model.stompMessageBody.msg;

import com.ssafy.mbting.api.request.AudioStageResult;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioStageResultBody {

    private AudioStageResult result;
}
