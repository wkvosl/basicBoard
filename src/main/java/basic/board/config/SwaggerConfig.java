package basic.board.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "에이~피~아이", version = "1.0", description = "스프링부트, vue")
)
public class SwaggerConfig {
}
