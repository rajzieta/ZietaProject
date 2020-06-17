package com.zietaproj.zieta.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "STATUS_MASTER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
public class StatusMaster implements Serializable{

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @NotNull
	 private Long client_id;
	    
	    @NotBlank
	    private String status;

	    @NotBlank
	    private String status_type;
	    
		@NotBlank
		private String created_by;

	    @Column(nullable = false, updatable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @CreatedDate
	    private Date created_date;

	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @LastModifiedDate
	    private Date modified_date;
		
		@NotBlank
		private String modified_by;
		
		private boolean IS_DELETE;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		

		public Long getClient_id() {
			return client_id;
		}

		public void setClient_id(Long client_id) {
			this.client_id = client_id;
		}

		public String getStatus_type() {
			return status_type;
		}

		public void setStatus_type(String status_type) {
			this.status_type = status_type;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCreated_by() {
			return created_by;
		}

		public void setCreated_by(String created_by) {
			this.created_by = created_by;
		}

		public Date getCreated_date() {
			return created_date;
		}

		public void setCreated_date(Date created_date) {
			this.created_date = created_date;
		}

		public Date getModified_date() {
			return modified_date;
		}

		public void setModified_date(Date modified_date) {
			this.modified_date = modified_date;
		}

		public String getModified_by() {
			return modified_by;
		}

		public void setModified_by(String modified_by) {
			this.modified_by = modified_by;
		}

		public boolean isIS_DELETE() {
			return IS_DELETE;
		}

		public void setIS_DELETE(boolean iS_DELETE) {
			IS_DELETE = iS_DELETE;
		}
	
		
	
}
