package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.service.TaskMasterService;

@RestController
@RequestMapping("/api")
public class TaskMasterController {
	@Autowired
	TaskMasterService taskmasterService;
	// Get All Tasks
	@GetMapping("/getAllTasks")
	public String getAllTasks() {
		String response="";
		try {
			List<TaskMasterDTO> taskMasters= taskmasterService.getAllTasks();
			System.out.println("taskMasters size=>"+taskMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(taskMasters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	
	  // Create a new Task
	  
	 // @PostMapping("/addTaskmaster") 
	  @RequestMapping(value = "addTaskmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	  public void addTaskmaster(@Valid @RequestBody TaskMaster taskmaster) { 
	   taskmasterService.addTaskmaster(taskmaster);
	   }
	  
	  // Get a Single Task
	  
		/*
		 * @GetMapping("/task_master/{id}") public Optional<TaskMaster>
		 * getTaskById(@PathVariable(value = "id") Long taskmasterId) { return
		 * taskMasterRepository.findById(taskmasterId); // .orElseThrow(() -> new
		 * ResourceNotFoundException("TaskMaster", "id", // taskId)); why optional here?
		 * }
		 */
	 

	// Update a Note
	
	/*
	 * @PutMapping("/task_master/{id}") public TaskMaster
	 * updateTaskmaster(@PathVariable(value = "id") Long taskmasterId,
	 * 
	 * @Valid @RequestBody TaskMaster taskmasterdetails) {
	 * 
	 * Optional<TaskMaster> taskmaster =
	 * taskMasterRepository.findById(taskmasterId); // // .orElseThrow(() -> new
	 * ResourceNotFoundException("TaskMaster", "id",taskmasterId));
	 * 
	 * taskmaster.setTask_type(taskmasterdetails.getTask_type());
	 * taskmaster.setClient_id(taskmasterdetails.getClient_id());
	 * 
	 * TaskMaster updatedTaskMaster = taskMasterRepository.save(taskmaster); return
	 * updatedTaskMaster; }
	 */
	 
	 
	// Delete a Note
	/*
	 * @DeleteMapping("/task_master/{id}") public ResponseEntity<?>
	 * deleteTaskMaster(@PathVariable(value = "id") Long taskmasterId) { TaskMaster
	 * taskmaster = taskMasterRepository.findById(taskmasterId) // .orElseThrow(()
	 * -> new ResourceNotFoundException("Note", "id", noteId));
	 * 
	 * taskMasterRepository.delete(taskmaster);
	 * 
	 * return ResponseEntity.ok().build(); }
	 */
	
}
