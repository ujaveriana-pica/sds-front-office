package co.edu.javeriana.pica.front.infraestructure.api;

import co.edu.javeriana.pica.front.core.entities.AuthUser;
import co.edu.javeriana.pica.front.infraestructure.exception.AuthException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    private static final String INTERNAL_TOKEN_HEADER = "x-auth-token";
    private static final String INTERNAL_TOKEN_SEPARATOR = ";";

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        if (context.getUriInfo().getPath().contains("/front-office/")) {
            /*
            if(!isValidJWTToken(context)) {
                context.abortWith(Response.status(Response.Status.FORBIDDEN).entity("FORBIDDEN").build());
            }
            */
            if(getUserInContext(context).isEmpty()) {
                context.abortWith(Response.status(Response.Status.FORBIDDEN).entity("FORBIDDEN").build());
            }
        }
    }

    /*
    private boolean isValidJWTToken(ContainerRequestContext context) {
        try {
            return getJWTToken(context).map(token -> {
                Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_PRIVATE_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer(Constants.JWT_ISSUER)
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                return true;
            }).orElse(false);
        } catch (JWTVerificationException exception) {
            logger.info("Error al validar token. Exception: {}", exception.getMessage());
            exception.printStackTrace();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }
    */

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

    private Optional<String> getJWTToken(ContainerRequestContext context) {
        String token = context.getHeaderString("Authorization");
        if(token != null) {
            token = token.replace("Bearer", "");
            token = token.replace("bearer", "");
            token = token.replace("BEARER", "");
            return Optional.of(token.trim());
        }
        return Optional.empty();
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
