package com.youcode.opinionhub.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/communities")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityController {
}
