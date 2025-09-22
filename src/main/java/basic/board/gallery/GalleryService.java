package basic.board.gallery;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

  private final GalleryRepository galleryRepository;
  private final JPAQueryFactory queryFactory;
    public GalleryService(GalleryRepository galleryRepository, JPAQueryFactory queryFactory) {
        this.galleryRepository = galleryRepository;
        this.queryFactory = queryFactory;
    }

    public GalleryEntity save(GalleryEntity galleryEntity) {
        return galleryRepository.save(galleryEntity);
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

    public Page<GalleryEntity> findAllBySearch(Pageable pageable, String category, String search) {

        QGalleryEntity galleryEntity = QGalleryEntity.galleryEntity;
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

        List<GalleryEntity> galleryList = queryFactory
                .selectFrom(galleryEntity)
                .where(builder)
                .orderBy(galleryEntity.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory
                        .select(galleryEntity.count())
                        .from(galleryEntity)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);


        return new PageImpl<>(galleryList, pageable, total);
    }

    public void deleteById(GalleryEntity galleryEntity) {

        galleryRepository.findById(galleryEntity.getGalleryNo())
                .orElseThrow(()-> new RuntimeException("삭제할 게시물이 없습니다."));

        galleryEntity.setDelYn("Y");
        galleryRepository.save(galleryEntity);

    }
}
