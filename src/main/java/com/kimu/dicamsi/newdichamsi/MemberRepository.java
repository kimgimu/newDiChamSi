package com.kimu.dicamsi.newdichamsi;

import com.kimu.dicamsi.newdichamsi.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);

}
