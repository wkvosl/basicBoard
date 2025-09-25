package basic.board.attachFile;

import basic.board.basicEntity.BasicEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "attach_file")
public class AttachFileEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_file_no")
    private Long attachFileNo;
    @Column(name = "gallery_no")
    private Long galleryNo;
    @Column(name = "path_name")
    private String pathName;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "size")
    private long size;
    @Column(name = "extension")
    private String extension;
    @Column(name = "resource_path_name")
    private String resourcePathName;

    public Long getAttachFileNo() {
        return attachFileNo;
    }

    public void setAttachFileNo(Long attachFileNo) {
        this.attachFileNo = attachFileNo;
    }

    public Long getGalleryNo() {
        return galleryNo;
    }

    public void setGalleryNo(Long galleryNo) {
        this.galleryNo = galleryNo;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getResourcePathName() {
        return resourcePathName;
    }

    public void setResourcePathName(String resourcePathName) {
        this.resourcePathName = resourcePathName;
    }
}
