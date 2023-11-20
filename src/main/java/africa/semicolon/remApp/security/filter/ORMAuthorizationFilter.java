package africa.semicolon.remApp.security.filter;

import africa.semicolon.remApp.exception.ORMException;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.utils.EndPointsConstant;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

//@RequiredArgsConstructor

public class ORMAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//    private int order;

    public ORMAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
//        this.order = order;
//        setOrder(order);
    }

//    private void setOrder(int order) {
//        this.order = order;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1 authorization");
        boolean isUnAuthorizedPath = EndPointsConstant.UNAUTHORIZED_ENDPOINTS.contains(request.getServletPath()) &&
                request.getMethod().equals(HttpMethod.POST.name());

        System.out.println(isUnAuthorizedPath);
        System.out.println(request.getServletPath());

        if (isUnAuthorizedPath) filterChain.doFilter(request, response);
        else  {
            try {
                authorizeRequest(request, response, filterChain);
            } catch (ORMException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("2 authorization");
        authorize(request);
        filterChain.doFilter(request, response);
    }

    private void authorize(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("3 authorization");
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        System.out.println("authorizationHeader - " + authorizationHeader);
        System.out.println("sub 1");
        String tokenPrefix = "Bearer ";
        System.out.println("sub 2");
        boolean isValidToken = authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix);
        System.out.println("sub 3");
        if (isValidToken) {
            String token = authorizationHeader.substring(tokenPrefix.length());
            authorizeToken(token);
        }
    }

    private void authorizeToken(String token) throws UnsupportedEncodingException {
        System.out.println("4 authorization");
        Map<String, Claim> map = jwtUtil.extractClaimsFromToken(token);
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        Claim roles = map.get("Roles");
        Claim userId = map.get("userId");
        Claim email = map.get("email");
        addClaimToUserAuthorities(grantedAuthorities, roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, email, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void addClaimToUserAuthorities(List<SimpleGrantedAuthority> grantedAuthorities, Claim roles) {
        System.out.println("5 authorization");
        for (int i = 0; i < roles.asMap().size(); i++) {
            String role = (String) roles.asMap().get("role" + (i + 1));
            if (role != null) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }
    }
}
