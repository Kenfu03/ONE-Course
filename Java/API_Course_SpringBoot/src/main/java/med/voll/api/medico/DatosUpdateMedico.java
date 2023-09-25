package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccion;

public record DatosUpdateMedico(
    @NotNull Long id,
    String nombre,
    String documento,
    DatosDireccion direccion) {
}
