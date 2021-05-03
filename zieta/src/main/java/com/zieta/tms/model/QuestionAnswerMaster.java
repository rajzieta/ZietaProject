package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "config_question_answer")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class QuestionAnswerMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "question_id")
	private Long questionId;
	

	@Column(name = "answer")
	private String answer;
	
	

}
