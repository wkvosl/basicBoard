package basic.board.gallery;

import basic.board.basicEntity.BasicEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "gallery")
public class GalleryEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_no")
    private Long galleryNo;
    @Column(name = "gallery_title")
    private String galleryTitle;
    @Column(name = "gallery_content")
    private String galleryContent;
    @Column(name = "gallery_writer")
    private String galleryWriter;
    @Column(name = "attach_file_no")
    private String attachFileNo;

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

    public String getAttachFileNo() {
        return attachFileNo;
    }

    public void setAttachFileNo(String attachFileNo) {
        this.attachFileNo = attachFileNo;
    }
}
