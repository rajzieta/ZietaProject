package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.response.ActivitiesByTaskResponse;

public interface ActivitiesByTaskService {

	List<ActivitiesByTaskResponse> getActivitesByClientProjectTaskOld(long clientId,long projectId,long taskId);
}
