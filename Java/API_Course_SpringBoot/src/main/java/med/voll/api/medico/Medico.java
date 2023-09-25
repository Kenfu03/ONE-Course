package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;

@Entity
@Table(name="medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String email;
  private String telefono;
  private String documento;
  private Boolean activo;
  @Enumerated(EnumType.STRING)
  private Especialidad especialidad;
  @Embedded
  private Direccion direccion;


  public Medico(DatosRegistroMedico datosRegistroMedico) {
    this.nombre = datosRegistroMedico.nombre();
    this.email = datosRegistroMedico.email();
    this.telefono = datosRegistroMedico.telefono();
    this.documento = datosRegistroMedico.documento();
    this.activo = true;
    this.especialidad = datosRegistroMedico.especialidad();
    this.direccion = new Direccion(datosRegistroMedico.direccion());
  }

  public void actualizarDatos(DatosUpdateMedico datosUpdateMedico) {
    if (datosUpdateMedico.nombre() != null){
      this.nombre = datosUpdateMedico.nombre();
    }
    if (datosUpdateMedico.documento() != null){
      this.documento = datosUpdateMedico.documento();
    }
    if (datosUpdateMedico.direccion() != null){
      this.direccion = direccion.actualizarDatos(datosUpdateMedico.direccion());
    }
  }

  public void desactivarMedico() {
    this.activo = false;
  }
}
