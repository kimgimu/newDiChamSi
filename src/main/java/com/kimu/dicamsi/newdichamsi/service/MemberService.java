package com.kimu.dicamsi.newdichamsi.service;

import com.kimu.dicamsi.newdichamsi.MemberRepository;
import com.kimu.dicamsi.newdichamsi.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public void joinMember(MemberDTO.request request){
        //암호화
        request.setPassword(encoder.encode(request.getPassword()));
        memberRepository.save(request.toEntity());
    }

    //아이디 중복검사
    public Boolean existsByUsername(String username){
        log.info("username : {}",username );
        if(username == null){
            throw new IllegalArgumentException("아이디를 입력하세요");
        }
        return memberRepository.existsByUsername(username);
    }
    //닉네임 중복검사
    public Boolean existsByNickname(String nickname){
        log.info("nickname : {}",nickname );
        if(nickname == null){
            throw new IllegalArgumentException("닉네임을 입력하세요");
        }
        return memberRepository.existsByNickname(nickname);
    }
    //이메일 중복검사
    public Boolean existsByEmail(String email){
        if(email == null){
            throw new RuntimeException("이메일을 입력하세요");
        }
        log.info("email : {}",email );
        return memberRepository.existsByEmail(email);
    }

    public Boolean checkDuplicates(String type,String request) {
        if(request == null) {
           throw new RuntimeException(type + "을(를) 입력하세오");
       }
       switch (type) {
           case "username" :
               return memberRepository.existsByUsername(request);
           case "nickname" :
               return memberRepository.existsByNickname(request);
           case "email" :
               return memberRepository.existsByEmail(request);
           default:
               throw new RuntimeException("해당 타입이 없습니다");
       }


    }



}
