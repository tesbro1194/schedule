package com.sparta.schedule.filter;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String url = request.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/plan/update/{planId}") || url.startsWith("/plan/delete/{planId}"))) {

            User user = (User) request.getAttribute("user");

            UserRoleEnum role = user.getRole();

            if (role != UserRoleEnum.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 일정 수정 및 삭제가 가능합니다.");
                return ;
            }

            filterChain.doFilter(request, response);

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
