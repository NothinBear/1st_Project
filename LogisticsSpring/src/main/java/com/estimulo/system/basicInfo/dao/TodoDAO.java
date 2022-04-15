package com.estimulo.system.basicInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.basicInfo.to.TodoTO;

public interface TodoDAO {
	public ArrayList<TodoTO> selectTodoList();

	public HashMap<String,Object> addNewTodo(String empCode, String empName, String status, String mag, String content);

	public void deleteTodo(String todoNum);
}
