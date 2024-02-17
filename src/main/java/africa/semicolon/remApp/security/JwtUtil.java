//package africa.semicolon.remApp.security;
//
//import africa.semicolon.remApp.enums.Role;
//import africa.semicolon.remApp.exception.ORMException;
//import africa.semicolon.remApp.exceptions.REMAException;
//import africa.semicolon.remApp.models.Company;
//import africa.semicolon.remApp.models.Employee;
//import africa.semicolon.remApp.services.company.CompanyService;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.io.UnsupportedEncodingException;
//import java.time.Instant;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;
//
//public class JwtUtil {
//
//    @Value(JWT_SECRET)
//    private String secret;
//
//    @Autowired
//    private CompanyService companyService;
//
//    public String generateAccessTokenAfterLogin(Employee employee, Role role) {
//       String email = employee.getEmail();
//
//        var listOfCurrentRoles = employee.getRoles();
//        listOfCurrentRoles.add(role);
//
//        int number = 1;
//        Map<String, String> map = new HashMap<>();
//        for (int i = 0; i < listOfCurrentRoles.size(); i++) {
//            map.put("role "+number, listOfCurrentRoles.toArray()[i].toString());
//            number++;
//        }
//        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
//                .withClaim("id", employee.getId())
//                .withClaim("Roles", map)
//                .withClaim("email", email)
//                .sign(Algorithm.HMAC512(secret.getBytes()));
//    }
//
//    public String generateAccessTokenAfterLogin(String email) {
//        Company foundCompany = companyService.findCompanyByEmail(email)
//                .orElseThrow(() -> new NoSuchElementException("Company not found for email: " + email));
//
//        String companyId = foundCompany.getUniqueID();
//        List<Role> roles = foundCompany.getRoles();
//        List<String> roleNames = roles.stream().map(Role::getName).toList();
//        Instant issuedAt = Instant.now();
//        Instant expiresAt = issuedAt.plusSeconds(86000L);
//        return JWT.create()
//                .withIssuedAt(Date.from(issuedAt))
//                .withExpiresAt(Date.from(expiresAt))
//                .withClaim("email", email)
//                .withClaim("uniqueID", companyId)
//                .withClaim("roles", roleNames)
//                .sign(Algorithm.HMAC512(secret.getBytes()));
//    }
//
//    public Map<String, Claim> extractClaimsFromToken(String token)  {
//        DecodedJWT decodedJWT = verifyToken(token);
//        return decodedJWT.getClaims();
//    }
//
//
//    private DecodedJWT validateToken(String token) {
//        return JWT.require(Algorithm.HMAC512(secret.getBytes())).build().verify(token);
//    }
//
//
//    public DecodedJWT verifyToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        return verifier.verify(token);
//    }
//
//
//}




package africa.semicolon.remApp.security;

import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.exception.ORMException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.services.company.CompanyService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.*;

import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;

public class JwtUtil {

    @Value(JWT_SECRET)
    private String secret;

    @Autowired
    private CompanyService companyService;

    public String generateAccessTokenAfterLogin(Employee employee, Role role) {
        String email = employee.getEmail();
        List<Role> listOfCurrentRoles = employee.getRoles();
        listOfCurrentRoles.add(role);

        Map<String, String> roleMap = new HashMap<>();
        for (int i = 0; i < listOfCurrentRoles.size(); i++) {
            roleMap.put("role " + (i + 1), listOfCurrentRoles.get(i).getName());
        }

        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("id", employee.getId())
                .withClaim("Roles", roleMap)
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String generateAccessTokenAfterLogin(String email) {
        Company foundCompany = companyService.findCompanyByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Company not found for email: " + email));

        String companyId = foundCompany.getUniqueID();
        List<Role> roles = foundCompany.getRoles();
        List<String> roleNames = roles.stream().map(Role::getName).toList();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(86000L);

        return JWT.create()
                .withIssuedAt(Date.from(issuedAt))
                .withExpiresAt(Date.from(expiresAt))
                .withClaim("email", email)
                .withClaim("uniqueID", companyId)
                .withClaim("roles", roleNames)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public Map<String, Claim> extractClaimsFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaims();
    }

    public DecodedJWT verifyToken(String token) {
        System.out.println("Token to Verify: " + token);
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        System.out.println("Secret.getBytes() => " + secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
