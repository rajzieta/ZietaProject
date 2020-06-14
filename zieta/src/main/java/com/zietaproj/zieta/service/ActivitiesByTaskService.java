package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.response.ActivitiesByTaskResponse;

public interface ActivitiesByTaskService {

	List<ActivitiesByTaskResponse> getActivitesByClientProjectTask(long clientId,long projectId,long taskId);
}
