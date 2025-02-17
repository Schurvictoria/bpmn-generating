package workflowGenerator.service;

import workflowGenerator.dto.LoginRequest;
import workflowGenerator.dto.RegisterRequest;
import workflowGenerator.dto.UserResponse;
import workflowGenerator.exception.UserAlreadyExistsException;
import workflowGenerator.model.User;
import workflowGenerator.repository.UserRepository;
import workflowGenerator.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateJwtToken(authentication);
    }
}