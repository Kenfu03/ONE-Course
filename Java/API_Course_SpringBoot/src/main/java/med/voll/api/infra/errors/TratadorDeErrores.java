package med.voll.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {


  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity tratarError404(){
    return ResponseEntity.notFound().build();
  }

  private record DatosErrorValidation(String campo, String error){
    public DatosErrorValidation(FieldError fieldError){
      this(fieldError.getField(), fieldError.getDefaultMessage());
    }
  }

  @ExceptionHandler(ValidacionDedIntegridad.class)
  public ResponseEntity errorHandlerValidaciones(Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity validationErrors(Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity error400(MethodArgumentNotValidException e){
    List<DatosErrorValidation> errorMessage = e.getFieldErrors().stream().map(DatosErrorValidation::new).toList();
    return ResponseEntity.badRequest().body(errorMessage);
  }


}
