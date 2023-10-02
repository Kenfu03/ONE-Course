package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HoraDeAnticipacion implements ValidadorConsultas{

  public void validar(DatosAgendarConsulta datosAgendarConsulta){
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime horaDeConsulta = datosAgendarConsulta.fecha();

    boolean diferencia30min = Duration.between(now, horaDeConsulta).toMinutes() < 30;

    if(diferencia30min){
      throw new ValidationException("Las consultas deben hacerse con almenos 30min de anticipacion");
    }
  }
}
