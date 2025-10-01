package basic.board.gallery;

import basic.board.attachFile.AttachFileDTO;
import basic.board.attachFile.AttachFileRequestDTO;
import basic.board.attachFile.AttachFileService;
import basic.board.attachFile.QAttachFileEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GalleryService {

    @Value("${file.download.url}")
    private String fileDownloadUrl;

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

    public GalleryWithFileDTO findById(Long id){
        QGalleryEntity galleryEntity = QGalleryEntity.galleryEntity;
        QAttachFileEntity attachFileEntity = QAttachFileEntity.attachFileEntity;

        //Gallery 조회
        GalleryEntity gallery = queryFactory
                .selectFrom(galleryEntity)
                .where(galleryEntity.galleryNo.eq(id))
                .fetchOne();

        if(gallery == null) {return  null;}

        //DTO 변환
        GalleryWithFileDTO result = new GalleryWithFileDTO();
        result.setGalleryNo(gallery.getGalleryNo());
        result.setGalleryTitle(gallery.getGalleryTitle());
        result.setGalleryContent(gallery.getGalleryContent());
        result.setGalleryWriter(gallery.getGalleryWriter());
        result.setRegDate(gallery.getRegDate());
        result.setDelYn(gallery.getDelYn());

        // AttachFile 조회
        List<AttachFileDTO> attachFiles = queryFactory
                .select(Projections.constructor(
                        AttachFileDTO.class,
                        attachFileEntity.attachFileNo,
                        attachFileEntity.galleryNo,
                        attachFileEntity.originalFileName,
                        attachFileEntity.resourcePathName
                ))
                .from(attachFileEntity)
                .where(attachFileEntity.galleryNo.eq(id)
                        .and(attachFileEntity.delYn.eq("N")))
                .fetch();

        //DTO에 파일 세팅
        result.setFiles(attachFiles);

        return result;

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

        //Gallery 조회
        List<GalleryEntity> galleryList = queryFactory
                .selectFrom(galleryEntity)
                .where(builder)
                .orderBy(galleryEntity.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //DTO 변환
        List<GalleryWithFileDTO> result = galleryList.stream().map(g -> {
            GalleryWithFileDTO dto = new GalleryWithFileDTO();
            dto.setGalleryNo(g.getGalleryNo());
            dto.setGalleryTitle(g.getGalleryTitle());
            dto.setGalleryContent(g.getGalleryContent());
            dto.setGalleryWriter(g.getGalleryWriter());
            dto.setRegDate(g.getRegDate());
            dto.setDelYn(g.getDelYn());
            return dto;
        }).toList();

        //AttachFile 조회 in절을 위한 갤러리 id 리스트
        List<Long> galleryNos = galleryList.stream().map(GalleryEntity::getGalleryNo).toList();

        //AttachFile 조회 in절
        List<AttachFileDTO> attachFiles = queryFactory
                .select(Projections.constructor(
                        AttachFileDTO.class,
                        attachFileEntity.attachFileNo,
                        attachFileEntity.galleryNo,
                        attachFileEntity.originalFileName,
                        attachFileEntity.resourcePathName
                ))
                .from(attachFileEntity)
                .where(attachFileEntity.galleryNo.in(galleryNos)
                        .and(attachFileEntity.delYn.eq("N")))
                .fetch();

        //GalleryWithFileDTO에 AttachFileDTO 매핑
        Map<Long, List<AttachFileDTO>> filesMap = attachFiles.stream()
                .collect(Collectors.groupingBy(AttachFileDTO::getGalleryNo));
        result.forEach(dto -> {
            List<AttachFileDTO> files = filesMap.get(dto.getGalleryNo());
            if(files != null) {
                dto.setFiles(files);
            }
        });

        Long total = Optional.ofNullable(
                queryFactory
                        .select(galleryEntity.count())
                        .from(galleryEntity)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);


        return new PageImpl<>(result, pageable, total);
    }

    public void deleteById(GalleryEntity galleryEntity) {

        galleryRepository.findById(galleryEntity.getGalleryNo())
                .orElseThrow(() -> new RuntimeException("삭제할 게시물이 없습니다."));

        galleryEntity.setDelYn("Y");
        galleryRepository.save(galleryEntity);

    }
}
