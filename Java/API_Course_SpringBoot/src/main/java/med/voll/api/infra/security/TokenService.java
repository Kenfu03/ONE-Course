package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.secret}")
  private String apiSecret;

  public String generarToken(User user){
    try {
      Algorithm algorithm = Algorithm.HMAC256(apiSecret);
      return JWT.create()
          .withIssuer("voll med")
          .withSubject(user.getLogin())
          .withClaim("id", user.getId())
          .withExpiresAt(generarExpiration())
          .sign(algorithm);
    } catch (JWTCreationException exception){
      throw new RuntimeException();
    }
  }

  public String getSubject(String token) {
    if (token == null){
      throw new RuntimeException();
    }
    DecodedJWT verifier = null;
    try {
      Algorithm algorithm = Algorithm.HMAC256(apiSecret);
      verifier = JWT.require(algorithm)
          .withIssuer("voll med")
          .build()
          .verify(token);
    } catch (JWTVerificationException exception) {
      System.out.println(exception.toString());
    }
    if (verifier == null || verifier.getSubject() == null) {
      throw new RuntimeException("Verifier invalido");
    }
    return verifier.getSubject();
  }

  private Instant generarExpiration(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
  }


}
