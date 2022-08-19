package com.ssafy.mbting.common.auth;

import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MemberDetailService implements UserDetailsService{
	@Autowired
    MemberService memberService;
	
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    		Member member = memberService.getUserByEmail(email);
    		if(member != null) {
    			MemberDetails userDetails = new MemberDetails(member);
    			return userDetails;
    		}
    		return null;
    }
}
