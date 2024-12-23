package com.lautaro_cenizo.crud.controller;


import com.lautaro_cenizo.security.auth.AuthenticationRequest;
import com.lautaro_cenizo.security.auth.AuthenticationResponse;
import com.lautaro_cenizo.security.auth.AuthenticationService;
import com.lautaro_cenizo.security.auth.RegistrationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException{
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        service.activateAccount(token);
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")  // Solo otros admins pueden crear admins
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }


}