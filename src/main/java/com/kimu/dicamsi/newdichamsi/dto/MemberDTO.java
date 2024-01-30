package com.kimu.dicamsi.newdichamsi.dto;

import com.kimu.dicamsi.newdichamsi.domain.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class request {
        private String username;
        private String password;
        private String nickname;
        private String name;
        private String email;

        public Member toEntity(){
            return Member
                    .builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .name(name)
                    .email(email)
                    .build();
        }
    }
}
