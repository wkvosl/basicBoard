package basic.board.attachFile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

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
    public long updateWithGalleryNo(AttachFileRequestDTO payload) {

        QAttachFileEntity attachFileEntity = QAttachFileEntity.attachFileEntity;

        long updatedCount =
                queryFactory
                .update(attachFileEntity)
                .set(attachFileEntity.galleryNo, payload.getGalleryId())
                .where(attachFileEntity.attachFileNo.in(payload.getFileIds()))
                .execute();

        return updatedCount;
    }

}
