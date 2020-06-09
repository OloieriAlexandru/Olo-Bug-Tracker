package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.JwtRequestDTO;
import com.olo.olobugtracker.dtos.JwtResponseDTO;
import com.olo.olobugtracker.services.abstractions.UserService;
import com.olo.olobugtracker.services.implementations.JwtUserDetailsService;
import com.olo.olobugtracker.utils.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@ApiOperation(value = "Endpoints for JWT authentification")
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("auth")
    @ApiOperation(value = "Creates a JWT token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authRequest) throws Exception {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails, userService.getUserId(authRequest.getUsername()));

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS", e);
        }
    }
}
