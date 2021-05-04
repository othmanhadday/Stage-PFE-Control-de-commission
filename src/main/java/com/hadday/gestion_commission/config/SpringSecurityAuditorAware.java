package com.hadday.gestion_commission.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

            return SecurityContextHolder.getContext().getAuthentication() != null
                    ? Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName())
                    :Optional.ofNullable("System");

    }
}
