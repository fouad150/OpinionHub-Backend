package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.UserRepository;
import com.youcode.opinionhub.Service.AuthenticationService;
import com.youcode.opinionhub.Service.JwtService;
import com.youcode.opinionhub.enums.Role;
import com.youcode.opinionhub.enums.TokenType;
import com.youcode.opinionhub.exception.AlreadyExistsException;
import com.youcode.opinionhub.payload.request.AuthenticationRequest;
import com.youcode.opinionhub.payload.request.RegisterRequest;
import com.youcode.opinionhub.payload.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service @Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    @Value("${upload.profile}")
    private String profileFolder;

    @Override
    public AuthenticationResponse register(RegisterRequest request, MultipartFile photo) throws IOException {

        if (photo == null) {
            throw new BadRequestException("profile is required.");
        }

        try {
            String filename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            byte[] bytes = photo.getBytes();
            Path path = Paths.get(profileFolder + File.separator + filename);
            Files.write(path, bytes);


            var user = User.builder()
                    .name(request.getName())
                    .usedName(request.getUsedName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .photoPath(path.toString())
                    .build();
            boolean userExists=userRepository.findByEmail(request.getEmail()).isPresent();
            if(userExists){
                throw new AlreadyExistsException("this user is already exists ");
            }

            user = userRepository.save(user);
            var jwt = jwtService.generateToken(user);

            var roles = user.getRole().getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .toList();

            return AuthenticationResponse.builder()
                    .accessToken(jwt)
                    .email(user.getEmail())
                    .id(user.getId())
                    .roles(roles)
                    .tokenType( TokenType.BEARER.name())
                    .build();


        } catch (IOException e) {
            throw new IOException("failed to save the photo");
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var roles = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .roles(roles)
                .email(user.getEmail())
                .id(user.getId())
                .tokenType( TokenType.BEARER.name())
                .build();
    }
}
