package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// JPA는 JPA만의 고유한 메모리 공간을 이용, 엔티티 객체들을 관리.
@MappedSuperclass // 이 클래스가 적용되면 테이블로 생성이 되지 않음.
@EntityListeners(value = {AuditingEntityListener.class })
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate",updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

    // JPA 내부에서 엔티티 객체가 생성/변경되는 것을 감지하는 역할은 AuditingEntityListener 로 이루어짐.
    //이를 통해서 regDate, modDate 에 적절한 값이 지정.
}
