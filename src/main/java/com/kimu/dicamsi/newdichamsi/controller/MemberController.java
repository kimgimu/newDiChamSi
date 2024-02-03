package com.kimu.dicamsi.newdichamsi.controller;

import com.kimu.dicamsi.newdichamsi.dto.MemberDTO;
import com.kimu.dicamsi.newdichamsi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join/test")
    public ResponseEntity<?> test(@RequestBody MemberDTO.request request) {
        System.out.println(request);
        log.info("{}",request);
        return ResponseEntity.ok(request);
    }

    @PostMapping(value ="/api/join/check/{field}")
    public ResponseEntity<?> checkDuplicates(@PathVariable("field") String field, @RequestBody MemberDTO.request request) {
        try {
            Boolean isValid;
            switch (field) {
                case "username" :
                    isValid = memberService.checkDuplicates(field,request.getUsername());
                    break;
                case "nickname" :
                    isValid = memberService.checkDuplicates(field,request.getNickname());
                    break;
                case "email" :
                    isValid = memberService.checkDuplicates(field,request.getEmail());
                    break;
                default:
                    return ResponseEntity.badRequest().body("지원하지 않는 필드 입니다");
            }
            log.info("{} 중복결과 값 : {}", field, isValid);
            if(isValid) {
                return ResponseEntity.badRequest().body("사용중인 " + field + " 입니다");
            }
            return ResponseEntity.ok("사용 가능한 "+field+" 입니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    //아이디 중복검사
//    @PostMapping("/api/join/check/username")
//    public ResponseEntity<?> checkUsername(@RequestBody MemberDTO.request request) {
//        try {
//            Boolean isValid = memberService.existsByUsername(request.getUsername());
//            log.info("request : {}", request);
//            if(isValid){
//                log.info("아이디 중복결과 값 : {}", isValid);
//                ResponseEntity.badRequest().body("사용중인 아이디입니다");
//            }
//            return ResponseEntity.ok("사용 가능한 아이디입니다");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    //닉네임 중복검사
//    @PostMapping("/api/join/check/nickname")
//    public ResponseEntity<?> checkNickname(@RequestBody MemberDTO.request request) {
//        try {
//            Boolean isValid = memberService.existsByNickname(request.getNickname());
//            log.info("request : {}", request);
//            if(isValid){
//                log.info("닉네임 중복결과 값 : {}", isValid);
//                ResponseEntity.badRequest().body("사용중인 닉네임입니다");
//            }
//            return ResponseEntity.ok("사용 가능한 닉네임입니다");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    //이메일 중복검사
//    @PostMapping("/api/join/check/email")
//    public ResponseEntity<?> checkEmail(@RequestBody MemberDTO.request request) {
//        try {
//            Boolean isValid = memberService.existsByEmail(request.getEmail());
//            log.info("request : {}", request);
//            if(isValid){
//                log.info("닉네임 중복결과 값 : {}", isValid);
//                ResponseEntity.badRequest().body("사용중인 이메일입니다");
//            }
//            return ResponseEntity.ok("사용 가능한 이메일입니다");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    //회원가입
    @PostMapping("/api/join")
    public ResponseEntity<?> join(@Valid @RequestBody MemberDTO.request request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("실패 원인 : {}", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        } else {
            memberService.joinMember(request);
            return ResponseEntity.ok("회원가입을 축하드립니다");
        }
    }


}
