package com.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SumAvg implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer divisionId;
	private String divisionName;
	private Integer count;
	private Integer sum;
	private BigDecimal avg;

	public SumAvg(Integer divisionId, String divisionName, Integer count, Integer sum, BigDecimal avg) {
		this.divisionId = divisionId;
		this.divisionName = divisionName;
		this.count = count;
		this.sum = sum;
		this.avg = avg;

	}

	public SumAvg(Object[] objects) {
		this((Integer) objects[0], (String) objects[1], (Integer) objects[2], (Integer) objects[3],
				(BigDecimal) objects[4]);
	}

}