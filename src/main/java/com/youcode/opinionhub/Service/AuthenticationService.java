package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.payload.request.AuthenticationRequest;
import com.youcode.opinionhub.payload.request.RegisterRequest;
import com.youcode.opinionhub.payload.response.AuthenticationResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request, MultipartFile photo) throws IOException;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws IOException;
}
