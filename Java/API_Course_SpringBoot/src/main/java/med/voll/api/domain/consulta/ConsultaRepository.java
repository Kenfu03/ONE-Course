package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

  Boolean existsByPacienteIdAndFechaBetween(Long paciente_id, LocalDateTime primeraHora, LocalDateTime ultimaHora);

  Boolean existsByMedicoIdAndFecha(Long medico_id, LocalDateTime fecha);
}
