package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.response.ActivitiesByTaskResponse;

public interface ActivitiesByTaskService {

	List<ActivitiesByTaskResponse> getActivitesByClientProjectTaskOld(long clientId,long projectId,long taskId);
}
