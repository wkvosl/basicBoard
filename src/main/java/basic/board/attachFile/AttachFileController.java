package basic.board.attachFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<AttachFileResultDTO> fileSave(@RequestPart("files") List<MultipartFile> files) throws IOException {

        List<AttachFileResultDTO> results = new ArrayList<>();

        if (files == null || files.isEmpty()) {
            return results;
        }

        for(MultipartFile file : files) {

            if(file.isEmpty()) {
                continue;
            }

//            if(!file.getContentType().startsWith("image")){
//                throw new RuntimeException("이미지 만");
//            }


            String fileName = file.getOriginalFilename();
            String ext = (fileName != null && fileName.contains(".")) ? fileName.substring(fileName.lastIndexOf(".")+1) : null;
            String newFileName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

            String realpath = uploadPath + newFileName;
            String resourcePath = downLoadPath + newFileName;

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

            results.add(new AttachFileResultDTO(attachFileEntity.getAttachFileNo()));
        }
        return results;
    }

    /*파일의 galleryNo 업데이트
    * 생성 or 삭제*/
    @PostMapping(value = "/file/update")
    public AttachFileResultDTO fileUpdate(@RequestBody AttachFileRequestDTO payload) {
        System.out.println(payload);
        long resultCnt = attachFileService.updateWithGalleryNo(payload);
       return new AttachFileResultDTO(resultCnt > 0);
    }

    @PostMapping(value="/file/delete/{id}")
    public AttachFileResultDTO uploadFileDelete(@PathVariable Long id){
        System.out.println(id);

        try{
            attachFileService.uploadFileDelete(id);
            return new AttachFileResultDTO(true);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
