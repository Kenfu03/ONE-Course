package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

  @Autowired
  private PacienteRepository pacienteRepository;

  @PostMapping
  public ResponseEntity<DatosRespuestaPaciente> registrarPacientes(
      @RequestBody
      @Valid
      DatosRegistroPaciente datosRegistroPaciente,
      UriComponentsBuilder uriComponentsBuilder) {
    Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
    DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);
    URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
    return ResponseEntity.created(uri).body(datosRespuestaPaciente);
  }

  @GetMapping
  public ResponseEntity<Page<DatosListadoPaciente>> listadoPaciente(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
//    return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);     Encontrar todos sin filtro

    // Encontrar medicos aplicando filtro de Activo = true
    return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));

  }

  @GetMapping("/{id}")
  public ResponseEntity<DatosRespuestaPaciente> listadoPorId(@PathVariable Long id) {
    Paciente paciente = pacienteRepository.getReferenceById(id);
    return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosUpdatePaciente datosUpdatePaciente) {
    Paciente paciente = pacienteRepository.getReferenceById(datosUpdatePaciente.id());
    paciente.actualizarDatos(datosUpdatePaciente);
    return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));

  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity eliminarPacienteLogico(@PathVariable Long id) {
    Paciente paciente = pacienteRepository.getReferenceById(id);
    paciente.desactivarPaciente();
    return ResponseEntity.noContent().build();
  }

//  public void eliminarMedicoPermanente(@PathVariable Long id) {
//    Medico medico = medicoRepository.getReferenceById(id);
//    medicoRepository.delete(medico);
//  }
}
