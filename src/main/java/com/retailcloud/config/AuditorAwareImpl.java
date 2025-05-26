package com.retailcloud.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.retailcloud.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	JwtService jwtService;

    @Override
    public Optional<String> getCurrentAuditor() {
    	String userId = jwtService.getUserIdFromRequest(request);
        return Optional.ofNullable(userId);
    }
}
