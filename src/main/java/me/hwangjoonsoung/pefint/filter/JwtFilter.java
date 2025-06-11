package me.hwangjoonsoung.pefint.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.util.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.service.user.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        String uri = request.getRequestURI();

        // 인증 제외 경로
        if (uri.startsWith("/auth") || uri.equals("/user/new")) {
            filterChain.doFilter(request, response); // JWT 검증 없이 통과
            return;
        }

        if (token != null && jwtProvider.validateToken(token)) {
            String subject = jwtProvider.getSubjectFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        String result = "";
        if (bearer != null) {
            result = bearer.startsWith("Bearer ") ? bearer.substring(7) : "";
        }
        return result;
    }
}
