package basic.board.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadPathInitializer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @PostConstruct
    public void init() throws IOException {
        Path path = Paths.get(fileUploadPath);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
    }
}
