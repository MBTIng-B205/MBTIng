package com.ssafy.mbting.db.initData;

import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.db.entity.*;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppMemberInitData {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;
    private final InterestMemberRepository interestMemberRepository;
    private final FriendRepository friendRepository;
    private final OpenviduService openviduService;
    private final MessageRepository messageRepository;
    @Value("${com.mbting.fileupload_uri}")
    private String fileuri;

    public void create() {

        List<Member> devs = new ArrayList<>();

        //wpdud.JPG
        Member d1 = Member.builder()
                .nickname("송제영")
                .email("wp29dud@naver.com")
                .gender(Gender.MALE)
                .mbti("ISTP")
                .birth(LocalDate.of(1994, 2, 9))
                .profileUrl(fileuri + "wpdud.jpg")
                .sido("대전")
                .build();
//        Member d2 = Member.builder()
//                .nickname("이기진")
//                .email("rlwls1101@hanmail.net")
//                .gender(Gender.MALE)
//                .mbti("ISTP")
//                .birth(LocalDate.of(1999, 2, 2))
//                .profileUrl(fileuri + "wpdud.jpg")
//                .sido("대전")
//                .build();
        Member f1 = Member.builder()
                .nickname("아이유")
                .email("dldb@naver.com")
                .gender(Gender.FEMALE)
                .mbti("INFJ")
                .birth(LocalDate.of(1993, 5, 16))
                .profileUrl(fileuri + "dldb.jpg")
                .sido("대전")
                .build();
        Member f2 = Member.builder()
                .nickname("수지")
                .email("tnwl@naver.com")
                .gender(Gender.FEMALE)
                .mbti("INFJ")
                .birth(LocalDate.of(1994, 10, 10))
                .profileUrl(fileuri + "tnwl.jpg")
                .sido("서울")
                .build();
        Member f3 = Member.builder()
                .nickname("우영우")
                .email("duddn@naver.com")
                .gender(Gender.FEMALE)
                .mbti("ISTJ")
                .profileUrl(fileuri + "duddn.jpg")
                .birth(LocalDate.of(1994, 3, 1))
                .sido("대전")
                .build();
        Member f4 = Member.builder()
                .nickname("동그라미")
                .email("ehdrm@naver.com")
                .gender(Gender.FEMALE)
                .mbti("ENTJ")
                .profileUrl(fileuri + "ehdrm.jpg")
                .birth(LocalDate.of(1994, 4, 4))
                .sido("대전")
                .build();
        Member f5 = Member.builder()
                .nickname("손석구")
                .email("tjrrn@naver.com")
                .gender(Gender.MALE)
                .mbti("ENFJ")
                .birth(LocalDate.of(1970, 9, 1))
                .profileUrl(fileuri + "tjrrn.png")
                .sido("대전")
                .build();
        Member f6 = Member.builder()
                .nickname("공효진")
                .email("gywls@naver.com")
                .gender(Gender.FEMALE)
                .mbti("ESTP")
                .profileUrl(fileuri + "gywls.jpg")
                .birth(LocalDate.of(1980, 4, 4))
                .sido("대전")
                .build();
        Member f7 = Member.builder()
                .nickname("혜리")
                .email("gPfl@naver.com")
                .gender(Gender.FEMALE)
                .mbti("ISTJ")
                .profileUrl(fileuri + "gPfl.jpg")
                .birth(LocalDate.of(1994, 4, 4))
                .sido("대전")
                .build();
        Member f8 = Member.builder()
                .nickname("송중기")
                .email("wndrl@naver.com")
                .gender(Gender.MALE)
                .mbti("INFJ")
                .birth(LocalDate.of(1985, 9, 19))
                .profileUrl(fileuri + "wndrl.jpg")
                .sido("서울")
                .build();
        Member f9 = Member.builder()
                .nickname("이광수")
                .email("rhkdtn@naver.com")
                .gender(Gender.MALE)
                .mbti("ENTJ")
                .birth(LocalDate.of(1985, 9, 19))
                .profileUrl(fileuri + "rhkdtn.jpg")
                .sido("서울")
                .build();

        devs.add(d1);
//        devs.add(d2);
        devs.add(f1);
        devs.add(f2);
        devs.add(f3);
        devs.add(f4);
        devs.add(f5);
        devs.add(f6);
        devs.add(f7);
        devs.add(f8);
        devs.add(f9);
        List<Member> fl = new ArrayList<>();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        fl.add(f4);
        fl.add(f5);
        fl.add(f6);
        fl.add(f7);
        fl.add(f8);
        fl.add(f9);
        memberRepository.saveAll(devs);
        List<Friend> friends = new ArrayList<>();
        fl.forEach((f) -> {
            friends.add(Friend.builder()
                    .fromId(d1)
                    .toId(f)
                    .build());
            friends.add(Friend.builder()
                    .fromId(f)
                    .toId(d1)
                    .build());
        });
//        fl.forEach((f) -> {
//            friends.add(Friend.builder()
//                    .fromId(d2)
//                    .toId(f)
//                    .build());
//            friends.add(Friend.builder()
//                    .fromId(f)
//                    .toId(d2)
//                    .build());
//        });
        friendRepository.saveAll(friends);
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().fromId(f7).toId(d1).readByTo(true).content("대전 어디사세요 제영님?").build());
        messages.add(Message.builder().fromId(f8).toId(d1).readByTo(true).content("제영님 내일 저녁 7시에 뵈요").build());
        messages.add(Message.builder().fromId(f9).toId(d1).readByTo(true).content("안녕하세요 반갑습니다").build());
        messages.add(Message.builder().fromId(f2).toId(d1).content("제영님 카톡 친추해요").build());
        messages.add(Message.builder().fromId(f3).toId(d1).content("송 투더 제 투더 영 ~ yeah").build());
        messages.add(Message.builder().fromId(f5).toId(d1).content("너 납치된거야").build());
        messages.add(Message.builder().fromId(f6).toId(d1).content("제영님 만나서 커피한잔 어떠세요").build());

        messages.add(Message.builder().fromId(d1).toId(f7).content("한밭대 앞에서 살고 있습니다").build());
        messages.add(Message.builder().fromId(d1).toId(f8).content("유성온천 할리스 앞에서 뵐게요").build());
        messages.add(Message.builder().fromId(d1).toId(f9).content("안녕하세요~ 자주 연락해요 저희").build());

//        messages.add(Message.builder().fromId(f7).toId(d2).readByTo(true).content("대전 어디사세요 제영님?").build());
//        messages.add(Message.builder().fromId(f8).toId(d2).readByTo(true).content("제영님 내일 저녁 7시에 뵈요").build());
//        messages.add(Message.builder().fromId(f9).toId(d2).readByTo(true).content("안녕하세요 반갑습니다").build());
//        messages.add(Message.builder().fromId(f2).toId(d2).content("제영님 카톡 친추해요").build());
//        messages.add(Message.builder().fromId(f3).toId(d2).content("송 투더 제 투더 영 ~ yeah").build());
//        messages.add(Message.builder().fromId(f5).toId(d2).content("너 납치된거야").build());
//        messages.add(Message.builder().fromId(f6).toId(d2).content("제영님 만나서 커피한잔 어떠세요").build());
//
//        messages.add(Message.builder().fromId(d2).toId(f7).content("한밭대 앞에서 살고 있습니다").build());
//        messages.add(Message.builder().fromId(d2).toId(f8).content("유성온천 할리스 앞에서 뵐게요").build());
//        messages.add(Message.builder().fromId(d2).toId(f9).content("안녕하세요~ 자주 연락해요 저희").build());


        messageRepository.saveAll(messages);

        List<Interest> interests = new ArrayList<>();

        Interest i1 = Interest.builder()
                .iname("TV/영화")
                .build();
        Interest i2 = Interest.builder()
                .iname("코딩")
                .build();
        Interest i3 = Interest.builder()
                .iname("술")
                .build();
        Interest i4 = Interest.builder()
                .iname("음악")
                .build();
        Interest i5 = Interest.builder()
                .iname("캠핑")
                .build();
        Interest i6 = Interest.builder()
                .iname("맛집탐방")
                .build();

        interests.add(i1);
        interests.add(i2);
        interests.add(i3);
        interests.add(i4);
        interests.add(i5);
        interests.add(i6);

        interestRepository.saveAll(interests);

        List<InterestMember> interestMembers = new ArrayList<>();

        devs.forEach((dev) -> {
            interests.forEach((interest -> {
                interestMembers.add(InterestMember.builder()
                        .interest(interest)
                        .member(dev)
                        .build());
            }));
        });

        interestMemberRepository.saveAll(interestMembers);


    }
}
