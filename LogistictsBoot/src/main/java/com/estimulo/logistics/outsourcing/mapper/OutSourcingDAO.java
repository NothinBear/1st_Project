package com.estimulo.logistics.outsourcing.mapper;

import java.util.ArrayList;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

@Mapper
public interface OutSourcingDAO {

	ArrayList<OutSourcingTO> selectOutSourcingList(HashMap<String,String>param);

}
