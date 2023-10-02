package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.validaciones.ValidadorConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errors.ValidacionDedIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
  @Autowired
  private PacienteRepository pacienteRepository;
  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private ConsultaRepository consultaRepository;
  @Autowired
  List<ValidadorConsultas> validadores;

  public DatosDetallesConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){
    if(!pacienteRepository.existsById(datosAgendarConsulta.idPaciente())){
      throw new ValidacionDedIntegridad("Este id para el paciente not found");
    }
    if (datosAgendarConsulta.idMedico()!=null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
      throw new ValidacionDedIntegridad("Este id para el medico not found");
    }

    validadores.forEach(validador -> validador.validar(datosAgendarConsulta));

    Paciente paciente = pacienteRepository.getReferenceById(datosAgendarConsulta.idPaciente());
    Medico medico = chooseMedico(datosAgendarConsulta);
    if (medico==null){
      throw new ValidacionDedIntegridad("No existen medicos disponibles para este horario y especialidad");
    }
    Consulta consulta = new Consulta(null, paciente, medico, datosAgendarConsulta.fecha());
    consultaRepository.save(consulta);

    return new DatosDetallesConsulta(consulta);
  }

  private Medico chooseMedico(DatosAgendarConsulta datosAgendarConsulta) {
    if(datosAgendarConsulta.idMedico()!=null){
      return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
    }
    if (datosAgendarConsulta.especialidad() == null) {
      throw new ValidacionDedIntegridad("Debe seleccionar una especialiad para el medico");
    }
    return medicoRepository.selectMedicoConEspecialidad(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
  }
}
