package africa.semicolon.remApp.security;

import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.models.Employee;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;

public class JwtUtil {

    @Value(JWT_SECRET)
    private String secret;

    public String generateAccessToken(Employee employee, Role role) {
        List<Role> listOfCurrentRoles = employee.getRoles();
        listOfCurrentRoles.add(role);

        int number = 1;
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < listOfCurrentRoles.size(); i++) {
            map.put("role "+number, listOfCurrentRoles.toArray()[i].toString());
            number++;
        }
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("id", employee.getId())
                .withClaim("Roles", map)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }


    public Map<String, Claim> extractClaimsFromToken(String token) throws UnsupportedEncodingException {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getClaims();
    }

    private DecodedJWT validateToken(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC512(secret.getBytes(secret))).build().verify(token);
    }
}
