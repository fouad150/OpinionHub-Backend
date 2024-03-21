package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.Service.AuthenticationService;
import com.youcode.opinionhub.Service.JwtService;
import com.youcode.opinionhub.enums.Role;
import com.youcode.opinionhub.payload.request.AuthenticationRequest;
import com.youcode.opinionhub.payload.request.RegisterRequest;
import com.youcode.opinionhub.payload.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")

@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestParam("name") String name,
                                                           @RequestParam("usedName") String usedName,
                                                           @RequestParam("email") String email,
                                                           @RequestParam("password") String password,
                                                           @RequestParam("photo") MultipartFile photo) throws IOException {
        RegisterRequest registerRequest=new RegisterRequest(name,usedName,email,password, Role.USER);
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest,photo);
        return ResponseEntity.ok()
                .body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok()
                .body(authenticationResponse);
    }





    @GetMapping("/info")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    }



 /*   @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){

    }*/
}
