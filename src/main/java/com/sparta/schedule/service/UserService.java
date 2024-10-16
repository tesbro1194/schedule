package com.sparta.schedule.service;

import com.sparta.schedule.config.PasswordEncoder;
import com.sparta.schedule.dto.SharerRequestDto;
import com.sparta.schedule.dto.SignupRequestDto;
import com.sparta.schedule.dto.UserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.entity.Sharer;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.repository.PlanRepository;
import com.sparta.schedule.repository.SharerRepository;
import com.sparta.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public String signup(SignupRequestDto requestDto, HttpServletResponse res) {
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

        User user = new User(username, password, email);
        userRepository.save(user);

        String token = jwtUtil.createToken(username);
        jwtUtil.addJwtToCookie(token, res);

        return token;
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

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다")
        );
    }

    public void registerSharer(SharerRequestDto requestDto) {
        Plan plan = planRepository.findById(requestDto.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("해당 Plan이 없습니다."));

        List<User> userList = requestDto.getUserList();
        for (User user : userList) {
            Sharer sharer = new Sharer();
            sharer.setUser(user);
            sharer.setPlan(plan);
            sharerRepository.save(sharer);
        }
    }
}