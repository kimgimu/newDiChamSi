package com.kimu.dicamsi.newdichamsi.service;

import com.kimu.dicamsi.newdichamsi.MemberRepository;
import com.kimu.dicamsi.newdichamsi.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public void memberJoin(MemberDTO.request request){
        //암호화
        request.setPassword(encoder.encode(request.getPassword()));
        memberRepository.save(request.toEntity());
    }


}
