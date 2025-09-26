package basic.board.gallery;

import basic.board.attachFile.AttachFileDTO;

import java.time.LocalDateTime;
import java.util.List;

public class GalleryWithFileDTO {
    private Long galleryNo;
    private String galleryTitle;
    private String galleryContent;
    private String galleryWriter;
    private LocalDateTime regDate;
    private String delYn;
    private List<AttachFileDTO> files;

    public GalleryWithFileDTO() {
    }

    public GalleryWithFileDTO(Long galleryNo, String galleryTitle, String galleryContent, String galleryWriter, LocalDateTime regDate, String delYn, List<AttachFileDTO> files) {
        this.galleryNo = galleryNo;
        this.galleryTitle = galleryTitle;
        this.galleryContent = galleryContent;
        this.galleryWriter = galleryWriter;
        this.regDate = regDate;
        this.delYn = delYn;
        this.files = files;
    }

    public Long getGalleryNo() {
        return galleryNo;
    }

    public void setGalleryNo(Long galleryNo) {
        this.galleryNo = galleryNo;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

    public String getGalleryContent() {
        return galleryContent;
    }

    public void setGalleryContent(String galleryContent) {
        this.galleryContent = galleryContent;
    }

    public String getGalleryWriter() {
        return galleryWriter;
    }

    public void setGalleryWriter(String galleryWriter) {
        this.galleryWriter = galleryWriter;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public List<AttachFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<AttachFileDTO> files) {
        this.files = files;
    }
}
