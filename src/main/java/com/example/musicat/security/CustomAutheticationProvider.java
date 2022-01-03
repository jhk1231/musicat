package com.example.musicat.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log
//인증에 대한 커스텀은 여기서 함
//UserDetailsService(CustomMemberDetailsService)에서 정보를 담은 memberContext를 AuthenticationManager에 넣기 위한 클래스(AuthenticationProvider 구현체)
public class CustomAutheticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPwd;

    //인증
    //authentication : AuthenticationManager로부터 전달받은 인증객체로, username, password가 담겨 있음
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("커스텀한 인증처리 하러 왔습니다.");

        String email = authentication.getName();
        String password = (String) authentication.getCredentials(); //Credentials : 비밀번호

        //loadUserByUsername : username(email) DB에 존재하는지 검증
        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(email);

        // 인코딩 된 password 일치 여부 검증
        if(!bCryptPwd.matches(password, memberContext.getMemberVo().getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }
        
        //인증 모두 완료될 경우 토큰 생성
        //인증 성공 시 authenticationToken에 회원 정보(비밀번호 제외한 회원 정보)를 담아 AuthenticationProvider를 호출한 AuthenticationManager에 전달
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberContext.getMemberVo(), null, memberContext.getAuthorities());
        log.info("인증 성공 - authenticationToken에 정보 담아서 AuthenticationManager에 넘김 : " + authenticationToken.getName());
        return authenticationToken;
    }

    //파라미터로 들어오는 authentication의 타입과 CustomAutheticationProvider 토큰의 타입이 일치할 때 CustomAutheticationProvider 클래스가 인증을 처리하도록 함
    //false이면 로그인 실패처리 됨
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
