package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.DTO.UserResponseDTO;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.youcode.opinionhub.convertor.UserConvertor.convertToUserResponseDTO;

@RestController
@RequestMapping("api/v1/resources/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam("email") String email) throws IOException {

        User foundUser=userService.getUserByEmail(email);
        UserResponseDTO user = convertToUserResponseDTO(foundUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
