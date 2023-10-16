package cloudcode.pedidos.control;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    public String handleException(final Throwable throwable, final Model model, HttpServletResponse response) {
        HttpStatus httpStatus;
        String errorMessage;

        if (throwable instanceof ChangeSetPersister.NotFoundException || throwable instanceof NoHandlerFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;

            errorMessage = "Opps não encontramos o que você está procurando";
        } else if (throwable instanceof HttpClientErrorException.Forbidden) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorMessage = "Access forbidden";
        } else if (throwable instanceof AccessDeniedException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMessage = "Acesso Negado";
        } else if (throwable instanceof BadCredentialsException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMessage = "Usuario ou senha incorreto";
            new RedirectView("/api/v1/login-error");

        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = "Internal server error";
        }
        logger.error("Exception during execution of SpringSecurity application", throwable);

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("httpStatus", httpStatus);
        model.addAttribute("httpStatusCode", httpStatus.value());

        return "error";
    }
}
