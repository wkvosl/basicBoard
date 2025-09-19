package basic.board;

import java.util.List;

public class BoardPageDTO<T>{

    private List<T> content;
    private PageInfo page;

    public BoardPageDTO(List<T> content, PageInfo page) {
        this.content = content;
        this.page = page;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

}
