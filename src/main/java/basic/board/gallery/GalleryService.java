package basic.board.gallery;

import basic.board.attachFile.AttachFileDTO;
import basic.board.attachFile.AttachFileRequestDTO;
import basic.board.attachFile.AttachFileService;
import basic.board.attachFile.QAttachFileEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GalleryService {

  private final GalleryRepository galleryRepository;
  private final JPAQueryFactory queryFactory;
  private final AttachFileService attachFileService;

    public GalleryService(GalleryRepository galleryRepository, JPAQueryFactory queryFactory, AttachFileService attachFileService) {
        this.galleryRepository = galleryRepository;
        this.queryFactory = queryFactory;
        this.attachFileService = attachFileService;
    }

    @Transactional
    public GalleryEntity save(GalleryEntity galleryEntity) {

        GalleryEntity result = galleryRepository.save(galleryEntity);

        if (result.getAttachFileNo() != null) {

            AttachFileRequestDTO requestDTO = new AttachFileRequestDTO();
            requestDTO.setGalleryId(result.getGalleryNo());

            List<Long> fileIds = Arrays.stream(result.getAttachFileNo().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            requestDTO.setFileIds(fileIds);

            attachFileService.updateWithGalleryNo(requestDTO);
        }

        return result;
    }


    public GalleryEntity findById(Long id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public Long countGalleryAllNotDeleted() {
        QGalleryEntity galleryEntity = QGalleryEntity.galleryEntity;

        return Optional.ofNullable(
                queryFactory
                        .select(galleryEntity.count())
                        .from(galleryEntity)
                        .where(galleryEntity.delYn.eq("N"))
                        .orderBy(galleryEntity.regDate.desc())
                        .fetchOne()
        ).orElse(0L);
    }

    public Page<GalleryWithFileDTO> findAllBySearch(Pageable pageable, String category, String search) {

        QGalleryEntity galleryEntity = QGalleryEntity.galleryEntity;
        QAttachFileEntity attachFileEntity = QAttachFileEntity.attachFileEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(galleryEntity.delYn.eq("N"));

        if(search != null && !search.isEmpty()) {
            switch (category) {
                case "title" -> builder.and(galleryEntity.galleryTitle.containsIgnoreCase(search));
                case "content" -> builder.and(galleryEntity.galleryContent.containsIgnoreCase(search));
                case "writer" -> builder.and(galleryEntity.galleryWriter.containsIgnoreCase(search));
                default -> builder.and(galleryEntity.galleryWriter.containsIgnoreCase(search)
                        .or(galleryEntity.galleryTitle.containsIgnoreCase(search))
                        .or(galleryEntity.galleryContent.containsIgnoreCase(search))
                );
            }
        }

        List<GalleryWithFileDTO> results = queryFactory
                .select(Projections.fields(
                        GalleryWithFileDTO.class,
                        galleryEntity.galleryNo,
                        galleryEntity.galleryTitle,
                        galleryEntity.galleryContent,
                        galleryEntity.galleryWriter,
                        galleryEntity.regDate,
                        galleryEntity.delYn
                ))
                .from(galleryEntity)
                .where(builder)
                .orderBy(galleryEntity.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

// files 채우기
        results.forEach(gallery -> {
            List<AttachFileDTO> files = queryFactory
                    .select(Projections.fields(
                            AttachFileDTO.class,
                            attachFileEntity.attachFileNo,
                            attachFileEntity.fileName,
                            attachFileEntity.pathName
                    ))
                    .from(attachFileEntity)
                    .where(attachFileEntity.galleryNo.eq(gallery.getGalleryNo())
                            .and(attachFileEntity.delYn.eq("N")))
                    .fetch();

            gallery.setFiles(files);
        });

        Long total = Optional.ofNullable(
                queryFactory
                        .select(galleryEntity.count())
                        .from(galleryEntity)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);


        return new PageImpl<>(results, pageable, total);
    }

    public void deleteById(GalleryEntity galleryEntity) {

        galleryRepository.findById(galleryEntity.getGalleryNo())
                .orElseThrow(() -> new RuntimeException("삭제할 게시물이 없습니다."));

        galleryEntity.setDelYn("Y");
        galleryRepository.save(galleryEntity);

    }
}
