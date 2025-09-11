package basic.board;

import basic.board.basicEntity.BasicEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class BoardEntity extends BasicEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_no")
    private Long boardNo;
    @Column(name = "board_title")
    private String boardTitle;
    @Column(name = "board_content")
    private String boardContent;
    @Column(name = "board_writer")
    private String boardWriter;

    @Column(name = "text_content")
    private String textContent;

    public Long getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(Long boardNo) {
        this.boardNo = boardNo;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
