package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements ValidadorConsultas{

  public void validar(DatosAgendarConsulta datosAgendarConsulta) {

    boolean domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());
    boolean beforeCloseTime = datosAgendarConsulta.fecha().getHour() < 7;
    boolean afterCloseTime = datosAgendarConsulta.fecha().getHour() > 19;

    if (domingo || beforeCloseTime || afterCloseTime) {
      throw new ValidationException("El horario de funcionamiento es de Lunes a Sabado de 7am a 7pm");
    }
  }
}
