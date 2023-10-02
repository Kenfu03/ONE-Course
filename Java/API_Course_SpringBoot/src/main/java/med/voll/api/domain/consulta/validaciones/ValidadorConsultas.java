package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;

public interface ValidadorConsultas {
  public abstract void validar(DatosAgendarConsulta datosAgendarConsulta);
}
