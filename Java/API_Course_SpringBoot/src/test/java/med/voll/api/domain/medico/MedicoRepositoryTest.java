package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
  void selectMedicoConEspecialidadEscenario1() {
    //GIVEN
    LocalDateTime proximoLunes10am = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10,0);

    Medico medico=registrarMedico("Jose", "jose@mail.com", "151512", Especialidad.CARDIOLOGIA);
    Paciente paciente=registrarPaciente("Antonio", "antonio@mail.com", "161513");
    registrarConsulta(medico, paciente, proximoLunes10am);

    // WHEN
    Medico medicoLibre = medicoRepository.selectMedicoConEspecialidad(Especialidad.CARDIOLOGIA, proximoLunes10am);

    //THEN
    assertThat(medicoLibre).isNull();

  }

  @Test
  @DisplayName("Deberia retornar un medico porque en ese horario no hay ninguna consulta")
  void selectMedicoConEspecialidadEscenario2() {
    //GIVEN
    LocalDateTime proximoLunes10am = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10,0);

    Medico medico=registrarMedico("Jose", "jose@mail.com", "151512", Especialidad.CARDIOLOGIA);

    //WHEN
    Medico medicoLibre = medicoRepository.selectMedicoConEspecialidad(Especialidad.CARDIOLOGIA, proximoLunes10am);

    //THEN
    assertThat(medicoLibre).isEqualTo(medico);

  }

  private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
    em.persist(new Consulta(null, paciente, medico,  fecha));
  }

  private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
    var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
    em.persist(medico);
    return medico;
  }

  private Paciente registrarPaciente(String nombre, String email, String documento) {
    var paciente = new Paciente(datosPaciente(nombre, email, documento));
    em.persist(paciente);
    return paciente;
  }

  private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
    return new DatosRegistroMedico(
        nombre,
        email,
        "61999999999",
        documento,
        especialidad,
        datosDireccion()
    );
  }

  private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
    return new DatosRegistroPaciente(
        nombre,
        email,
        "61999999999",
        documento,
        datosDireccion()
    );
  }

  private DatosDireccion datosDireccion() {
    return new DatosDireccion(
        " loca",
        "azul",
        "acapulpo",
        "321",
        "12"
    );
  }
}