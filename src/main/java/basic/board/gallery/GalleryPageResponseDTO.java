package basic.board.gallery;

import basic.board.page.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class GalleryPageResponseDTO<T>{

    private List<T> content;
    private PageInfo page;

    public GalleryPageResponseDTO(List<T> content, PageInfo page) {
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
