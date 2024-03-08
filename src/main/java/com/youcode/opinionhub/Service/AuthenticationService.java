package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.payload.request.AuthenticationRequest;
import com.youcode.opinionhub.payload.request.RegisterRequest;
import com.youcode.opinionhub.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
