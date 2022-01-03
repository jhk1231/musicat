package com.example.musicat.security;

import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.service.member.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service("userDetailsService")
public class CustomMemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    //loadUserByUsername : 인증을 시도하는 사용자의 ID(username)가 DB에 있는지 확인
    //UserDetails 타입으로 member 정보 반환해야 함(Session > spring session > authenticate 객체 안에 들어갈 수 있는 타입은 2가지
    //authenticate 객체 안에 들어갈 수 있는 타입은 2가지 1. UserDetails(일반 로그인), 2. OAuth2User(OAuth 로그인)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //view에서 받은 email을 사용해 사용자 정보가 있는지 조회하는 memberService 메소드
        MemberVO memberVo = memberService.retrieveMemberByEmail(email);

        //정보 없으면 예외 발생
        if(memberVo == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException, memberVo == null");
        }

        //GrantedAuthority : 현재 사용자가 가지고 있는 권한을 의미. GrantedAuthority객체는 특정 자원(url)에 대한 권한이 있는지를 검사
        //이번 프로젝트에서는 회원당 role이 하나씩이나 추후 확장성을 고려하여(회원 등급 추가되어 회원 당 role이 여러 개가 될 경우) List로 role을 받음
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(memberVo.getGrade())); //role

        MemberContext memberContext = new MemberContext(memberVo, roles);
        log.info("----" + roles.toString() + "----" + memberContext.getMemberVo().getEmail() + "----" + memberContext.getMemberVo().getPassword());

        return memberContext;
    }


}
