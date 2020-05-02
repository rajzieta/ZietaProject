package com.zietaproj.zieta.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "PROJECT_MASTER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)

public class ProjectMaster implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private Long client_id;

	    @NotBlank
	    private String project_type;
	    
		@NotBlank
		private String CREATED_BY;

	    @Column(nullable = false, updatable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @CreatedDate
	    private Date created_date;

	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @LastModifiedDate
	    private Date modified_date;
		
		@NotBlank
		private String MODIFIED_BY;
		
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

		public String getProject_type() {
			return project_type;
		}

		public void setProject_type(String project_type) {
			this.project_type = project_type;
		}

		public String getCREATED_BY() {
			return CREATED_BY;
		}

		public void setCREATED_BY(String cREATED_BY) {
			CREATED_BY = cREATED_BY;
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

		public String getMODIFIED_BY() {
			return MODIFIED_BY;
		}

		public void setMODIFIED_BY(String mODIFIED_BY) {
			MODIFIED_BY = mODIFIED_BY;
		}

		public boolean isIS_DELETE() {
			return IS_DELETE;
		}

		public void setIS_DELETE(boolean iS_DELETE) {
			IS_DELETE = iS_DELETE;
		}
		
		
	
	
}
