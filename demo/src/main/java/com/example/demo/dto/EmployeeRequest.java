package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class EmployeeRequest implements Serializable {

	/**
	 * 社員画像
	 */
	@NotEmpty(message="ファイルを選択してください")
	private String image;
	
	/**
	 * 名前
	 */
	@NotEmpty(message="名前を入力してください")
	@Size(max = 100,message="名前は50文字以内で入力してください")
	private String name;
	
	/**
	 * 性別
	 */
	@Size(max=3,message="性別は男か女で入力してください")
	private String sex;
	
	/**
	 * 部署
	 */
	@Size(max=30,message="部署は30文字以内で入力してください")
	private String division;
	
	/**
	 * 生年月日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth_date;
	
	/**
	 * メールアドレス
	 */
	@Email
	@NotBlank
	private String email;
	
	/**
	 * 電話番号
	 */
	private String phone;
	
	/**
	 * 住所
	 */
	@Size(max=250,message="住所は250字以内で入力してください")
	private String  address;
	
	/**
	 * 最終学歴
	 * 
	 */
	@Size(max=250,message="最終学歴は250字以内で入力してください")
	private String background;
	
	/**
	 * 特技
	 */
	@Size(max=250,message="特技は250字以内で入力してください")
	private String skill;
	/**
	 * 平仮名
	 */

	 @NotBlank(message = "読み仮名をカタカナを入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "読み仮名はカタカナで空白は入れないでください")
	private String hira;

	private Boolean delete_flg;

	@NotBlank(message = "部署カタカナを入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "部署はカタカナで空白は入れないでください")
	private String  divisionkana;

	private String yubin;
	private String banchi;
}
