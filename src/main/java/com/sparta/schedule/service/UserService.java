package com.sparta.schedule.service;

import com.sparta.schedule.config.PasswordEncoder;
import com.sparta.schedule.dto.*;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.entity.Sharer;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.repository.PlanRepository;
import com.sparta.schedule.repository.SharerRepository;
import com.sparta.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SharerRepository sharerRepository;
    private final PasswordEncoder passwordEncoder;
    public final JwtUtil jwtUtil;

    public String signup(SignupRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        UserRoleEnum role;
        if (requestDto.isRole()) {
            role = UserRoleEnum.ROLE_USER;
        } else {
            role = UserRoleEnum.ROLE_ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);

        String token = jwtUtil.createToken(username, role);
        response.setHeader("Authentication-Info", token);

        return token;
    }

    public String login(LoginRequestDto requestDto, HttpServletResponse response) throws IOException {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return "이메일을 입력하세요";
        }
        if (password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return  "비밀번호를 입력하세요";
        }

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return "이메일 또는 비밀번호가 잘못 되었습니다";
            }

            UserRoleEnum role = user.getRole();
            String token = jwtUtil.createToken(user.getUsername(), role);
            response.setHeader("AUTHORIZATION_HEADER", token);

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

        return "redirect:/plan/get-all";
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserResponseDto(user)).toList();
    }

    public String updateUser(Long id, UserRequestDto requestDto) {
        User user = findUserById(id);
        return user.update(requestDto);
    }

    public String deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.deleteById(id);
        return "유저 정보가 삭제되었습니다";
    }

    public Plan registerSharer(SharerRequestDto requestDto) {
        Plan plan = planRepository.findById(requestDto.getPlanId()).orElseThrow(() -> new IllegalArgumentException("해당 Plan이 없습니다."));

        List<User> userList = requestDto.getUserList();
        for (User user : userList) {
            Sharer sharer = new Sharer();
            sharer.setUser(user);
            sharer.setPlan(plan);
            sharerRepository.save(sharer);
        }
        return plan;
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다"));
    }
}