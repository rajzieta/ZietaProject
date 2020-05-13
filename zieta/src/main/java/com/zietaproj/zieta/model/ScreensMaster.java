package com.zietaproj.zieta.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "SCREENS_MASTER")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
       // allowGetters = true)
public class ScreensMaster implements Serializable {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String screen_code;

	    @NotBlank
	    private String screen_category;

	    @NotBlank
	    private String screen_desc;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getScreen_code() {
			return screen_code;
		}

		public void setScreen_code(String screen_code) {
			this.screen_code = screen_code;
		}

		public String getScreen_category() {
			return screen_category;
		}

		public void setScreen_category(String screen_category) {
			this.screen_category = screen_category;
		}

		public String getScreen_desc() {
			return screen_desc;
		}

		public void setScreen_desc(String screen_desc) {
			this.screen_desc = screen_desc;
		}
	    
	    
}
