package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.response.TasksByUserModel;

public interface TasksByUserService {

	List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId);

}
