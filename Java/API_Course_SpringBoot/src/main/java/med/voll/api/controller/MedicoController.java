package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository medicoRepository;

  @PostMapping
  public void resgistrarMedicos(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
    medicoRepository.save(new Medico(datosRegistroMedico));
  }

  @GetMapping
  public Page<DatosListadoMedico> listadoMedico(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion){
//    return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);     Encontrar todos sin filtro

    // Encontrar medicos aplicando filtro de Activo = true
    return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);

  }

  @PutMapping
  @Transactional
  public void actualizarMedico(@RequestBody @Valid DatosUpdateMedico datosUpdateMedico){
    Medico medico = medicoRepository.getReferenceById(datosUpdateMedico.id());
    medico.actualizarDatos(datosUpdateMedico);

  }

  @DeleteMapping("/{id}")
  @Transactional
  public void eliminarMedicoLogico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    medico.desactivarMedico();
  }

//  public void eliminarMedicoPermanente(@PathVariable Long id) {
//    Medico medico = medicoRepository.getReferenceById(id);
//    medicoRepository.delete(medico);
//  }
}
