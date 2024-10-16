package com.sparta.schedule.controller;

import com.sparta.schedule.dto.SharerRequestDto;
import com.sparta.schedule.dto.UserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public String createUser (@RequestBody UserRequestDto requestDto) {
        userService.createUser(requestDto);
        return "redirect:/plan/get-all";
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

    @PostMapping("/registerSharer")
    public String registerSharer (@RequestBody SharerRequestDto requestDto) {
        userService.registerSharer(requestDto);
        return "redirect:/plan/get-all";
    }
}
