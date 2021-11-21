package co.edu.javeriana.pica.front.infraestructure.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("La peticion no se encuentra autenticada");
    }
}
