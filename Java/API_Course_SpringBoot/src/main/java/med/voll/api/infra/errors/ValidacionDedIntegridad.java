package med.voll.api.infra.errors;

public class ValidacionDedIntegridad extends RuntimeException {
  public ValidacionDedIntegridad(String mensaje) {
    super(mensaje);
  }
}
