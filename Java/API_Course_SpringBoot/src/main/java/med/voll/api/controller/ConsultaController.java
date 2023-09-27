package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

  @PostMapping
  @Transactional
  public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){
    System.out.println(datosAgendarConsulta);
    return ResponseEntity.ok(new DatosDetallesConsulta(datosAgendarConsulta));
  }
}
