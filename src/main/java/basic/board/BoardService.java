package basic.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardEntity save(BoardEntity boardEntity) {
        return boardRepository.save(boardEntity);
    }

    public BoardEntity findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Page<BoardEntity> findAllBySearch(Pageable pageable,String category, String search) {

        // 검색어가 없으면 전체 조회
        if (search == null || search.isEmpty()) {
            return boardRepository.findAll(pageable);
        }

        // 카테고리가 없거나 "all"이면 제목, 내용, 작성자 모두 검색
        if (category == null || category.isEmpty()) {
            return boardRepository.findAllByBoardTitleContainingOrBoardContentContainingOrBoardWriterContaining(pageable, search, search, search);
        }

        // 카테고리에 따라 검색
        return switch (category) {
            case "title" -> boardRepository.findAllByBoardTitle(search, pageable);
            case "content" -> boardRepository.findAllByBoardContent(search, pageable);
            case "writer" -> boardRepository.findAllByBoardWriter(search, pageable);
            default ->
                // 안전하게 전체 검색 fallback
                    boardRepository.findAllByBoardTitleContainingOrBoardContentContainingOrBoardWriterContaining(pageable, search, search, search);
        };

    }

}
