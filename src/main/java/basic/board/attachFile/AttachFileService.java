package basic.board.attachFile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private final JPAQueryFactory queryFactory;

    public AttachFileService(AttachFileRepository attachFileRepository, JPAQueryFactory queryFactory) {
        this.attachFileRepository = attachFileRepository;
        this.queryFactory = queryFactory;
    }

    public void attachFileSave(AttachFileEntity attachFileEntity) {

        try {
            attachFileRepository.save(attachFileEntity);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * (galleryNo) 매핑을 위한 파일의 업데이트
     * */
    public long updateWithGalleryNo(AttachFileRequestDTO dto) {

        QAttachFileEntity attachFileEntity = QAttachFileEntity.attachFileEntity;

        long updatedCount =
                queryFactory
                .update(attachFileEntity)
                .set(attachFileEntity.galleryNo, dto.getGalleryId())
                .where(attachFileEntity.attachFileNo.in(dto.getFileIds()))
                .execute();

        return updatedCount;
    }

    //업로드 파일 삭제
    public void uploadFileDelete(Long id){

        Optional<AttachFileEntity> delfile = attachFileRepository.findById(id);
        if(delfile.isEmpty()) return;

        AttachFileEntity fileEntity = delfile.get();
        String filePath = fileEntity.getPathName();

        File file = new File(filePath);
        if(file.exists()) {
            boolean deleted = file.delete();
            if(!deleted){
                System.out.println("삭제실패");
            }
        }

        attachFileRepository.deleteById(id);

    }
}
