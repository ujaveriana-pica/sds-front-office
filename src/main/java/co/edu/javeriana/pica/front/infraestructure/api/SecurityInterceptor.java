package co.edu.javeriana.pica.front.infraestructure.api;

import co.edu.javeriana.pica.front.core.entities.AuthUser;
import co.edu.javeriana.pica.front.infraestructure.exception.AuthException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Base64;
import java.util.Optional;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private static final String INTERNAL_TOKEN_HEADER = "x-auth-token";
    private static final String INTERNAL_TOKEN_SEPARATOR = ";";

    @Override
    public void filter(ContainerRequestContext context) {
        if (context.getUriInfo().getPath().contains("/front-office/") && getUserInContext(context).isEmpty()) {
            context.abortWith(Response.status(Response.Status.FORBIDDEN).entity("FORBIDDEN").build());
        }
    }

    private Optional<AuthUser> getUserInContext(ContainerRequestContext context) {
        String base64Token = context.getHeaderString(INTERNAL_TOKEN_HEADER);
        return getInternalToken(base64Token).flatMap(token -> tokenToUser(token));
    }


    private static Optional<AuthUser> tokenToUser(String token) {
        return Optional.ofNullable(token).map(t -> {
            String[] arrTokens = t.split(INTERNAL_TOKEN_SEPARATOR);
            return Optional.of(new AuthUser(arrTokens[0].trim(), arrTokens[1].trim()));
        }).orElse(Optional.empty());
    }

    private static Optional<String> getInternalToken(String base64Token) {
        if(base64Token != null) {
            String token = new String(Base64.getDecoder().decode(base64Token));
            if(token.split(INTERNAL_TOKEN_SEPARATOR).length == 2) {
                return Optional.of(token.trim());
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public static AuthUser getAuthUser(HttpHeaders headers) {
        String base64Token = headers.getHeaderString(INTERNAL_TOKEN_HEADER);
        return getInternalToken(base64Token).flatMap(token -> tokenToUser(token))
                .orElseThrow(() -> new AuthException());
    }
}
