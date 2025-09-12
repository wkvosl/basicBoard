package basic.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAllByBoardTitle(String boardTitle, Pageable pageable);
    Page<BoardEntity> findAllByBoardContent(String boardContent, Pageable pageable);
    Page<BoardEntity> findAllByBoardWriter(String boardWriter, Pageable pageable);
    Page<BoardEntity> findAllByBoardTitleContainingOrBoardContentContainingOrBoardWriterContaining(Pageable pageable, String boardTitle, String boardContent, String boardWriter);
}
