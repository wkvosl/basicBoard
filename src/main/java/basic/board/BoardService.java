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

    public Page<BoardEntity> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

}
