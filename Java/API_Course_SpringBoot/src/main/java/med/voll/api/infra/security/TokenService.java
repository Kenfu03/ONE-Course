package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

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

  private Instant generarExpiration(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
  }
}
