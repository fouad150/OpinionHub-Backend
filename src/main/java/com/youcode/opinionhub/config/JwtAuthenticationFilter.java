package com.youcode.opinionhub.config;

import com.youcode.opinionhub.Service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; /** implementation is provided in config.ApplicationSecurityConfig */

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = jwtService.getJwtFromCookies(request);
        final String authHeader = request.getHeader("Authorization");
        System.out.println("jwt: "+ jwt);
        System.out.println("start with bearer: "+authHeader);
        if((jwt == null && (authHeader ==  null || !authHeader.startsWith("Bearer "))) || request.getRequestURI().contains("/auth")){
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(1);

        // If the JWT is not in the cookies but in the "Authorization" header
        if (jwt == null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // after "Bearer "
        }
        System.out.println(2);

        final String userEmail =jwtService.extractUserName(jwt);

        System.out.println(userEmail);
        System.out.println("..."+SecurityContextHolder.getContext().getAuthentication()==null);
        System.out.println("---"+SecurityContextHolder.getContext().getAuthentication());
        if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println(3);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("user:"+userDetails);
            if(jwtService.isTokenValid(jwt, userDetails)){

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                context.setAuthentication(authToken);

                SecurityContextHolder.setContext(context);
                System.out.println("context holder: "+ SecurityContextHolder.getContext());
            }
        }
        filterChain.doFilter(request,response);
    }
}
