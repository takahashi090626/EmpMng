package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmployeeUpdateRequest extends EmployeeRequest implements Serializable{
	
	/**
	 * 社員情報更新リクエスト
	 */
	@NotNull
	private Long id;
}
