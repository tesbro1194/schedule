package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class SharerRequestDto {
    private List<User> userList;
}
