//package com.roamy.config;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
///**
// * Created by Abhijit on 27/10/15.
// */
//@Component
//public class SpringSecurityAuditAware implements AuditorAware<String> {
//
//
//    @Override
//    public String getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        return authentication.getName();
//    }
//}
