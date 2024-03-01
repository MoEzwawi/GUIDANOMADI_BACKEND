package MoEzwawi.GUIDANOMADI_BACKEND.security;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.UnauthorizedException;
import MoEzwawi.GUIDANOMADI_BACKEND.services.UsersService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UsersService usersService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Bearer token required in \"Authorization\" header!");
            } else {
                String accessToken = authHeader.substring(7);
                this.jwtTools.verifyToken(accessToken);
                String id = this.jwtTools.extractIdFromToken(accessToken);
                User user = this.usersService.findById(UUID.fromString(id));
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException | NotFoundException | UnauthorizedException ex){
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return (
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                new AntPathMatcher().match("/auth/**", path)
        );
    }


}
