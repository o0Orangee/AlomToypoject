package alom.toyproject.service;

import alom.toyproject.domain.User;
import alom.toyproject.dto.UserInfoDto;
import alom.toyproject.dto.UserLoginDto;
import alom.toyproject.dto.UserRegistrationDto;
import alom.toyproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;    //이거어떻게쓰지

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*회원가입: 이메일, 닉네임, 패스워드 받아 신규 사용자 등록
    * 패스워드는 저장 시에 암호화
    * 중복 이메일 ㄴㄴ*/
    public UserInfoDto registerNewUser(UserRegistrationDto userRegistrationDto) {
        if(userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()){
            throw new IllegalStateException("이미 등록된 이메일입니다");
        }

//        새로운 유저 저장
        User newUser = new User();
        newUser.setEmail(userRegistrationDto.getEmail());
        newUser.setNickname(userRegistrationDto.getNickname());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(newUser);

//        저장된 유저 정보 리턴(이메일, 닉네임)
        UserInfoDto savedUser = new UserInfoDto();
        savedUser.setEmail(newUser.getEmail());
        savedUser.setNickname(newUser.getNickname());

        return savedUser;
    }

    /*로그인: 이메일, 패스워드로 로그인
    * 로그인 성공 시 사용자 정보(이메일, 닉네임) 반환*/
    public UserInfoDto loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("등록된 이메일이 아닙니다"));
//        이메일 입력 오류

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }   //비밀번호 불일치

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setNickname(user.getNickname());

        return userInfoDto;
    }

}
