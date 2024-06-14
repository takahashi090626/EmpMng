// package com.example.demo.service;

// // 必要なクラスをインポートします
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.example.demo.entity.Administrator;
// import com.example.demo.entity.User;
// import com.example.demo.repository.UserInfoRepository;

// import jakarta.transaction.Transactional;

// @Service // このクラスがサービス層のクラスであることを示します
// public class LoginService implements UserDetailsService { // UserDetailsServiceインターフェースを実装しています

//     @Autowired // Springが自動的にUserRepositoryの実装を注入します
//     private UserInfoRepository userinfoRepository;

//     @Autowired // Springが自動的にPasswordEncoderの実装を注入します
//     private PasswordEncoder passwordEncoder;

//     @Override // UserDetailsServiceインターフェースのメソッドを上書きします
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Administrator admin = userinfoRepository.findByEmail(email); // ユーザー名でユーザーを検索します
//         if (admin == null) {
//             throw new UsernameNotFoundException("User not found"); // ユーザーが見つからない場合、例外をスローします
//         }
//         return new UserPrincipal(admin); // ユーザーが見つかった場合、UserPrincipalを作成し返します
//     }

//     //新たにメソッドを追加します
//     public User findByUsername(String username) {
//         return userinfoRepository.findByEmail(email); // ユーザー名でユーザーを検索し返します
//     }

//     @Transactional // トランザクションを開始します。メソッドが終了したらトランザクションがコミットされます。
//     public void save(Administrator administrator) {
//         // UserDtoからUserへの変換
//         Administrator admin = new Admistrator();
//         admin.getEmail(Administrator.getEmail());
//         // パスワードをハッシュ化してから保存
//         admin.setPassword(passwordEncoder.encode(Administrator.getPassword()));

//         // データベースへの保存
//         userinfoRepository.save(admin); // UserRepositoryを使ってユーザーをデータベースに保存します
//     }
// }