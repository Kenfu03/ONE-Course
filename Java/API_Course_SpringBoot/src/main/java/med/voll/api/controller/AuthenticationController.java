package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.user.DatosAuthUser;
import med.voll.api.domain.user.User;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;
  @PostMapping
  public ResponseEntity authenticateUser(@RequestBody @Valid DatosAuthUser datosAuthUser){
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAuthUser.login(), datosAuthUser.password());
    Authentication userAuth = authenticationManager.authenticate(authenticationToken);
    String JWTtoken = tokenService.generarToken((User) userAuth.getPrincipal());
    return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
  }
}
