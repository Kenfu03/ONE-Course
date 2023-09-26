package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository medicoRepository;

  @PostMapping
  public ResponseEntity<DatosRespuestaMedico> resgistrarMedicos(
      @RequestBody
      @Valid
      DatosRegistroMedico datosRegistroMedico,
      UriComponentsBuilder uriComponentsBuilder) {
    Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
    DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
    URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(uri).body(datosRespuestaMedico);
  }

  @GetMapping
  public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
//    return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);     Encontrar todos sin filtro

    // Encontrar medicos aplicando filtro de Activo = true
    return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));

  }

  @GetMapping("/{id}")
  public ResponseEntity<DatosRespuestaMedico> listadoPorId(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    return ResponseEntity.ok(new DatosRespuestaMedico(medico));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosUpdateMedico datosUpdateMedico) {
    Medico medico = medicoRepository.getReferenceById(datosUpdateMedico.id());
    medico.actualizarDatos(datosUpdateMedico);
    return ResponseEntity.ok(new DatosRespuestaMedico(medico));

  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity eliminarMedicoLogico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    medico.desactivarMedico();
    return ResponseEntity.noContent().build();
  }

//  public void eliminarMedicoPermanente(@PathVariable Long id) {
//    Medico medico = medicoRepository.getReferenceById(id);
//    medicoRepository.delete(medico);
//  }
}
