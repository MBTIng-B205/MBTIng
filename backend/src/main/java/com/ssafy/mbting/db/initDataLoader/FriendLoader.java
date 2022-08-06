package com.ssafy.mbting.db.initDataLoader;

import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.FriendRepository;
import com.ssafy.mbting.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendLoader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private @Value("${com.mbting.ddl_auto}") String ddlAuto;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        if ("create".equalsIgnoreCase(ddlAuto)) {
            List<Member> gongs = new ArrayList<>();
            List<Member> kangs = new ArrayList<>();
            for (int i = 1; i <= 7; i++){
                gongs.add(Member.builder()
                        .nickname("효진" + i)
                        .email("hyojin@gong.com" + i)
                        .gender(false)
                        .mbti("INFJ")
                        .build());
                kangs.add(Member.builder()
                        .nickname("하늘" + i)
                        .email("haneul@kang.com" + i)
                        .gender(true)
                        .mbti("ESTP")
                        .build());
            }
            memberRepository.saveAll(gongs);
            memberRepository.saveAll(kangs);

            logger.info("\n\n{}명의 효진#과 {}명의 하늘#이 들어감\n", gongs.size(), kangs.size());

            List<Friend> friends = new ArrayList<>();
            gongs.forEach((gong) -> {
                kangs.forEach((kang) -> {
                    friends.add(Friend.builder()
                            .fromId(gong)
                            .toId(kang)
                            .build());
                    friends.add(Friend.builder()
                            .fromId(kang)
                            .toId(gong)
                            .build());
                });
            });
            friendRepository.saveAll(friends);

            logger.info("\n\n{}개의 친추가 들어감\n", friends.size());
        }
    }
}
