package basic.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        //todo 파일업로드, 외래키로 받아서
        this.boardService = boardService;
    }

    //저장
    public BoardEntity save(BoardEntity boardEntity) {

        return boardService.save(boardEntity);
    }
    //상세
    @GetMapping("/board/{id}")
    public BoardEntity findById(@PathVariable Long id) {
        return boardService.findById(id);
    }
    //전체목록

    @GetMapping("/board/list")
    public BoardPageDTO<BoardEntity> getBoardPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("boardNo").descending());
        Page<BoardEntity> boardPage = boardService.findAll(pageable);

        PageInfo pageInfo = new PageInfo(
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalPages(),
                boardPage.getTotalElements(),
                boardPage.getNumberOfElements(),
                boardPage.isFirst(),
                boardPage.isLast(),
                boardPage.isEmpty()
        );
        return new BoardPageDTO<>(boardPage.getContent(), pageInfo);
    }
}
