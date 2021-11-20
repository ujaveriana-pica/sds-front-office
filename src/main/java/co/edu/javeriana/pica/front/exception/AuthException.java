package co.edu.javeriana.pica.front.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("La peticion no se encuentra autenticada");
    }
}
