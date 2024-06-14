package com.example.demo.entity;  // このファイルが属するパッケージ（フォルダ）

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

// 以下の部分はデータベースとのやり取りに必要な情報を持つためのものです。


@Entity  // これはデータベースのテーブルを表しています
@Table(name = "user")  // このクラスが対応するテーブルの名前は "Users" です
public class User {

    @Id  // これが各ユーザを一意に識別するためのIDとなります
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDは自動的に増加します
    @Column(name = "id")  // データベースに合わせてカラム名を修正
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)  // "username" カラム。各ユーザーのユーザー名を表します。同じ名前のユーザーは存在できません
    private String username;

    @Column(name = "password", nullable = false)  // "password" カラム。ユーザーのパスワードを表します
    private String password;

    @Column(name = "email", nullable = false, unique = true)  // "email" カラム。ユーザーのメールアドレスを表します。同じメールアドレスのユーザーは存在できません
    private String email;

    // 以下は各値を取得するためのメソッド（ゲッター）です。
    public Integer getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    // 以下は各値を設定するためのメソッド（セッター）です。
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

