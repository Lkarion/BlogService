package com.example.blogservice.configuration;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.entity.BlogUserSession;
import com.example.blogservice.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        BlogUserSession session = sessionRepository.findBySessionId(header);
        if (session == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (session.getExpirationTime().isBefore(LocalDateTime.now())) {
            sessionRepository.delete(session);
            filterChain.doFilter(request, response);
            return;
        }

        KeyDTO keyDTO = KeyDTO.builder()
                .sessionId(header)
                .blogUser(session.getBlogUser())
                .build();

//        var role = session.getBlogUser().getIsAdmin() ? Role.ADMIN : Role.USER;

        Authentication key = new UsernamePasswordAuthenticationToken(
                keyDTO,
                null,
                session.getBlogUser().getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(key);

        filterChain.doFilter(request, response);
    }
}
