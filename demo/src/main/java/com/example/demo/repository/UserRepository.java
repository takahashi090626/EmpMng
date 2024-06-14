package com.example.demo.repository;

 // このファイルが属するパッケージ（フォルダ）

// 必要なツールをインポートしています

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.User;


// Userクラスを使うためにインポートしています

// UserRepositoryというインターフェースを作成します。JpaRepositoryを拡張して、UserオブジェクトとそれらのIDとしてLong型を扱えるようにします。
public interface UserRepository extends JpaRepository<User, Integer> {
    // ユーザー名でユーザーを探すメソッド。ユーザー名をパラメータとして渡すと、そのユーザー名を持つユーザーをデータベースから探して返します。
    User findByEmail(String email);
}



