package com.kimu.dicamsi.newdichamsi.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
//MappedSuperclass를 써야 JPA에서 자식클래스에서 상속되어 매핑이 적용된다
@MappedSuperclass
//시간에 관해 자동으로 값을 넣어주는 준다
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
