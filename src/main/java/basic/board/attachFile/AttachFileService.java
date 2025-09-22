package basic.board.attachFile;

import org.springframework.stereotype.Service;

@Service
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;

    public AttachFileService(AttachFileRepository attachFileRepository) {
        this.attachFileRepository = attachFileRepository;
    }

    public boolean attachFileSave(AttachFileEntity attachFileEntity) {

        try {
            attachFileRepository.save(attachFileEntity);
            return true;
        }catch (RuntimeException e){
            return false;
        }

    }

}
