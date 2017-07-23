package io.github.semhas.web.rest;

import io.github.semhas.security.jwt.JWTConfigurer;
import io.github.semhas.security.jwt.TokenProvider;
import io.github.semhas.service.DosenService;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.service.dto.DosenDTO;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.github.semhas.web.rest.vm.LoginVM;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final MahasiswaService mahasiswaService;
    private final DosenService dosenService;

    private final String MAHASISWA_KEY = "semhas.mhsw";
    private final String DOSEN_KEY = "semhas.dosn";

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, MahasiswaService mahasiswaService, DosenService dosenService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.mahasiswaService = mahasiswaService;
        this.dosenService = dosenService;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            Map<String, Object> claims = new HashMap<>();
            MahasiswaDTO mhs = mahasiswaService.findByUserLogin(loginVM.getUsername());
            claims.put(MAHASISWA_KEY, mhs == null ? null: mhs.getId());
            DosenDTO dosen = dosenService.findByUserLogin(loginVM.getUsername());
            claims.put(DOSEN_KEY, dosen == null ? null : dosen.getId());
            String jwt = tokenProvider.createToken(authentication, rememberMe, claims);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
