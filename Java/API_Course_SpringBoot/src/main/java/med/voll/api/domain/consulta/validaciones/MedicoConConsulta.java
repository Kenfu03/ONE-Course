package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorConsultas{

  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DatosAgendarConsulta datosAgendarConsulta){
    Boolean medicoConConsulta = consultaRepository.
        existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fecha());
    if(medicoConConsulta){
      throw new ValidationException("Medico ocupado a esa hora");
    }
  }
}
