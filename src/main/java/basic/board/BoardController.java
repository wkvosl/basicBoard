package basic.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        //todo 파일업로드, 외래키로 받아서
        this.boardService = boardService;
    }

    //저장 및 업데이트
    @PostMapping("/board/save")
    public BoardEntity save(@RequestBody BoardEntity boardEntity) {
        if(boardEntity.getBoardNo() > 0){
            boardEntity.setLastUpdateDate(LocalDateTime.now());
        }else{
            boardEntity.setRegDate(LocalDateTime.now());
        }
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
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String category
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("boardNo").descending());
        Page<BoardEntity> boardPage = boardService.findAllBySearch(pageable, category, search);

        PageInfo pageInfo = new PageInfo(
                boardPage.getNumber()+1,
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
