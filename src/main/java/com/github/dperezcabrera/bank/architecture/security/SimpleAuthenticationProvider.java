package com.github.dperezcabrera.bank.architecture.security;

import com.github.dperezcabrera.bank.architecture.auth.repositories.UserRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class SimpleAuthenticationProvider implements AuthenticationProvider {

	private DataSource dataSource;
	private UserRepository userRepository;
	private Environment env;

	@Override
	@Transactional(readOnly = true)
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName().toLowerCase();
		String password = authentication.getCredentials().toString();
		if (authenticate(username, password)) {
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		} else {
			throw new BadCredentialsException("Usuario o contraseña incorrecta");
		}
	}

	private boolean authenticate(@NonNull String username, @NonNull String password) {
		if ("admin".equalsIgnoreCase(username)) {
			return password.equals(env.getProperty("admin.password"));
		} else {
			boolean secure = true;
			return (secure) ? secureAuth(username, password) : insecureAuth(username, password);
		}
	}

	private boolean insecureAuth(String username, String password) {
		try {
			Connection c = dataSource.getConnection();
			Statement st = c.createStatement();
			return st.executeQuery("SELECT id FROM users WHERE username = '" + username + "' and password = '" + password + "';").next();
		} catch (SQLException e) {
			throw new BadCredentialsException("Usuario o contraseña incorrecta", e);
		}
	}

	private boolean secureAuth(String username, String password) {
		return userRepository.auth(username, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
