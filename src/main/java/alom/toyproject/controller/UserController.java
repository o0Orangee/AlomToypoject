package alom.toyproject.controller;

import alom.toyproject.domain.User;
import alom.toyproject.dto.UserInfoDto;
import alom.toyproject.dto.UserLoginDto;
import alom.toyproject.dto.UserRegistrationDto;
import alom.toyproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")    //뭐라써야되지
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User user = userService.registerNewUser(userRegistrationDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> loginUser(@RequestBody UserLoginDto userLoginDto) {
        UserInfoDto userInfoDto = userService.loginUser(userLoginDto);
        return ResponseEntity.ok(userInfoDto);
    }

}
