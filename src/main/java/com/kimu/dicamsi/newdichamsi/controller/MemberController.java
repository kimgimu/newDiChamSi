package com.kimu.dicamsi.newdichamsi.controller;

import com.kimu.dicamsi.newdichamsi.dto.MemberDTO;
import com.kimu.dicamsi.newdichamsi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@Valid @RequestBody MemberDTO.request request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        } else {
            memberService.memberJoin(request);
            return ResponseEntity.ok("회원가입을 축하드립니다");
        }
    }

}
