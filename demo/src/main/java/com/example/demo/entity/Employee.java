package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="employee")
public class Employee implements Serializable {
	
	/**
	 * 社員情報
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	/**
	 * 画像
	 */
	@Column(name="image")
	private String image;
	
	/**
	 *名前 
	 */
	@Column(name="name")
	private String name;
	
	/**
	 * 部署
	 */
	@Column(name="division")
	private String division;
	
	/**
	 * 性別
	 */
	@Column(name="sex")
	private String sex;
	
	/**
	 * メールアドレス
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * 電話番号
	 */
	@Column(name="phone")
	private String phone;
	
	/**
	 * 生年月日
	 */
	@Column(name="birth_date")
	private Date birth_date;
	
	/**
	 * 住所
	 */
	@Column(name="address")
	private String address;
	
	/**
	 * 最終学歴
	 */
	@Column(name="educational_background")
	private String background;
	
	/**
	 * 特技
	 */
	@Column(name="skill")
	private String skill;
	
	/**
	 * 平仮名
	 */
	@Column(name="hira_name")
	private String hira;

	/**
	 * 削除フラグ
	 */
	@Column(name="delete_flg", columnDefinition = "boolean default false")
	private Boolean deleteFlg = false;
	
	// getter メソッドの修正
	public boolean getDeleteFlg() {
		return deleteFlg != null ? deleteFlg : false;
	}

	/*
	 * 部署カタカナ
	 */
	@Column(name="divisionkana")
	private String divisionkana;

	@Column(name="yubin")
	private String yubin;

	@Column(name="banchi")
	private String banchi;
}
