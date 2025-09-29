package basic.board.attachFile;

public class AttachFileDTO {

    private Long attachFileNo;
    private Long galleryNo;
    private String originalFileName;
    private String resourcePathName;

    public AttachFileDTO(Long attachFileNo, Long galleryNo, String originalFileName, String resourcePathName) {
        this.attachFileNo = attachFileNo;
        this.galleryNo = galleryNo;
        this.originalFileName = originalFileName;
        this.resourcePathName = resourcePathName;
    }

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

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getResourcePathName() {
        return resourcePathName;
    }

    public void setResourcePathName(String resourcePathName) {
        this.resourcePathName = resourcePathName;
    }
}
