package blog.platform.config.JWT;

import blog.platform.domain.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	private final JwtCore jwtCore;

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String jwt;
		String username = null;
		String headerAuth = request.getHeader("Authorization");
		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			jwt = headerAuth.substring(7);
			try {
				username = jwtCore.getUsername(jwt);
			} catch (ExpiredJwtException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT has expired");
				return;
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
}
