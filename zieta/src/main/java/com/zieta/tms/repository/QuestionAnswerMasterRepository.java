package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.QuestionAnswerMaster;

@Repository
public interface QuestionAnswerMasterRepository extends JpaRepository<QuestionAnswerMaster, Long>{

	List<QuestionAnswerMaster> findByIsDelete(short notDeleted);
	
	List<QuestionAnswerMaster> findByClientId(long clientId);
	
    QuestionAnswerMaster findByQuestionIdAndClientId(long qMsterId,long clientId);
    
    @Query("select qam from QuestionAnswerMaster qam where qam.clientId=?2 and qam.answer >=?1 order by qam.answer asc")
	List<QuestionAnswerMaster> findByAmountAndClientId(String amount, long clientId);
	
	

	

}
