package com.estimulo.system.basicInfo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.system.basicInfo.to.TodoTO;

@Mapper
public interface TodoDAO {
	public ArrayList<TodoTO> selectTodoList();

	public HashMap<String,Object> addNewTodo(String empCode, String empName, String status, String mag, String content);

	public void deleteTodo(String todoNum);
}
