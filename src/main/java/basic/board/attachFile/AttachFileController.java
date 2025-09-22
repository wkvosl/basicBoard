package basic.board.attachFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class AttachFileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.download.url}")
    private String downLoadPath;

    private final AttachFileService attachFileService;

    public AttachFileController(AttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    @PostMapping(value = "/file/save")
    public boolean fileSave(@RequestPart("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return false;
        }

        String realpath = uploadPath + file.getOriginalFilename();
        String resourcePath = downLoadPath + file.getOriginalFilename();

        String fileName = file.getOriginalFilename();
        String ext = (fileName != null && fileName.contains(".")) ? fileName.substring(fileName.lastIndexOf(".")+1) : null;
        String newFileName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
        long fileSize = file.getSize();

        //파일 저장
        String saveFilePath = uploadPath + newFileName;
        File saveFile = new File(saveFilePath);
        file.transferTo(saveFile);

        AttachFileEntity attachFileEntity = new AttachFileEntity();
        attachFileEntity.setPathName(realpath);
        attachFileEntity.setFileName(newFileName);
        attachFileEntity.setOriginalFileName(fileName);
        attachFileEntity.setSize(fileSize);
        attachFileEntity.setExtension(ext);
        attachFileEntity.setResourcePathName(resourcePath);
        attachFileEntity.setRegDate(LocalDateTime.now());

        attachFileService.attachFileSave(attachFileEntity);

        return true;
    }
}
