package com.ssafy.mbting.common.searchSpec;

import com.ssafy.mbting.db.entity.Friend;
import org.springframework.data.jpa.domain.Specification;

public class FriendSearchSpec {
    public static Specification<Friend> withNickname(String nickname){
        return (Specification<Friend>) ((root, query, builder) ->
                builder.equal(root.get("nickname"), nickname)
                );
    }
}
