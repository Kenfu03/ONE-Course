package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteSinConsulta implements ValidadorConsultas{

  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DatosAgendarConsulta datosAgendarConsulta){
    LocalDateTime primeraHora = datosAgendarConsulta.fecha().withHour(7);
    LocalDateTime ultimaHora = datosAgendarConsulta.fecha().withHour(18);
    Boolean pacienteConConsulta = consultaRepository.
        existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(), primeraHora, ultimaHora);
    if(pacienteConConsulta){
      throw new ValidationException("Solamente se permite una cita por dia");
    }
  }
}
