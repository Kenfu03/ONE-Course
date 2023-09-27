package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity
@Table(name="pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String email;
  private String telefono;
  private String documento;
  private Boolean activo;
  @Embedded
  private Direccion direccion;

  public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
    this.nombre = datosRegistroPaciente.nombre();
    this.email = datosRegistroPaciente.email();
    this.telefono = datosRegistroPaciente.telefono();
    this.documento = datosRegistroPaciente.documento();
    this.activo = true;
    this.direccion = new Direccion(datosRegistroPaciente.direccion());
  }

  public void actualizarDatos(DatosUpdatePaciente datosUpdatePaciente) {
    if (datosUpdatePaciente.nombre() != null){
      this.nombre = datosUpdatePaciente.nombre();
    }
    if (datosUpdatePaciente.documento() != null){
      this.documento = datosUpdatePaciente.documento();
    }
    if (datosUpdatePaciente.direccion() != null){
      this.direccion = direccion.actualizarDatos(datosUpdatePaciente.direccion());
    }
  }

  public void desactivarPaciente() {
    this.activo = false;
  }
}
