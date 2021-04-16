package com.hadday.gestion_commission.security;

import com.hadday.gestion_commission.PasswordEncoder;
import com.hadday.gestion_commission.entities.UserApp;
import com.hadday.gestion_commission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class UsernamePasswordAuthFilter
        extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            UserApp userApp = userRepository.findUserAppByUsername(currentUserName);
            if (userApp == null || userApp.isDeleted() == true) {
                userApp.setDeleted(false);
                userApp.setActivate(false);
                userApp.setPermissions(null);
                userApp.setRoles(null);
                userApp.setPassword(passwordEncoder.getBCPE().encode("maroclear"));
                userRepository.save(userApp);
            }
        }
        filterChain.doFilter(request, response);
    }
}
