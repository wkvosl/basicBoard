package basic.board;

import basic.board.basicEntity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BoardEntity extends BasicEntity {

    @Id
    @GeneratedValue
    private Long board_no;
    private String board_title;
    private String board_content;
    private String board_writer;

    public Long getBoard_no() {
        return board_no;
    }

    public void setBoard_no(Long board_no) {
        this.board_no = board_no;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_writer() {
        return board_writer;
    }

    public void setBoard_writer(String board_writer) {
        this.board_writer = board_writer;
    }
}
