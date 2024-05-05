package com.smartscore.board.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	// 공통 컬럼
	private String createdBy;
	private String updatedBy;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdDate;


	@LastModifiedDate
    @Column
//	@Column(nullable = false)
	private LocalDateTime updatedDate;
}
