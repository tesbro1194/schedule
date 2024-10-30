package com.sparta.schedule.controller;

import com.sparta.schedule.dto.*;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup (@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }


    @GetMapping("/get-all")
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @PatchMapping("/update/param")
    public String updateUser(@RequestParam Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/delete/param")
    public String deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/registerSharer")
    public Plan registerSharer (@RequestBody SharerRequestDto requestDto) {
        return userService.registerSharer(requestDto);
    }
}
