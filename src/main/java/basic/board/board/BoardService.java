package basic.board.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final JPAQueryFactory queryFactory;
    public BoardService(BoardRepository boardRepository, JPAQueryFactory queryFactory) {
        this.boardRepository = boardRepository;
        this.queryFactory = queryFactory;
    }

    public BoardEntity save(BoardEntity boardEntity) {
        return boardRepository.save(boardEntity);
    }

    public BoardEntity findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Long countBoardAllNotDeleted() {
        QBoardEntity boardEntity = QBoardEntity.boardEntity;

        return Optional.ofNullable(
                queryFactory
                        .select(boardEntity.count())
                        .from(boardEntity)
                        .where(boardEntity.delYn.eq("N"))
                        .fetchOne()
        ).orElse(0L);
    }

    public Page<BoardEntity> findAllBySearch(Pageable pageable, String category, String search) {

       QBoardEntity boardEntity = QBoardEntity.boardEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(boardEntity.delYn.eq("N"));

        if(search != null && !search.isEmpty()) {
            switch (category) {
                case "title" -> builder.and(boardEntity.boardTitle.containsIgnoreCase(search));
                case "content" -> builder.and(boardEntity.boardContent.containsIgnoreCase(search));
                case "writer" -> builder.and(boardEntity.boardWriter.containsIgnoreCase(search));
                default -> builder.and(boardEntity.boardWriter.containsIgnoreCase(search)
                        .or(boardEntity.boardTitle.containsIgnoreCase(search))
                        .or(boardEntity.boardContent.containsIgnoreCase(search))
                );
            }
        }

        List<BoardEntity> boardList = queryFactory
                .selectFrom(boardEntity)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory
                        .select(boardEntity.count())
                        .from(boardEntity)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);


        return new PageImpl<>(boardList, pageable, total);
    }

    public void deleteById(BoardEntity boardEntity) {

        boardRepository.findById(boardEntity.getBoardNo())
                .orElseThrow(()-> new RuntimeException("삭제할 게시물이 없습니다."));

        boardEntity.setDelYn("Y");
        boardRepository.save(boardEntity);

    }
}
