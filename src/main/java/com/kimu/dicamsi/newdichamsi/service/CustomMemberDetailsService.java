package com.kimu.dicamsi.newdichamsi.service;

import com.kimu.dicamsi.newdichamsi.MemberRepository;
import com.kimu.dicamsi.newdichamsi.domain.Member.CustomMemberDetails;
import com.kimu.dicamsi.newdichamsi.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("로그인서비스 실행");
        System.out.println(username);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                new UsernameNotFoundException(username + "으로 된 사용자가 존재하지 않습니다"));
        System.out.println(member);
        return new CustomMemberDetails(member);
    }

}
