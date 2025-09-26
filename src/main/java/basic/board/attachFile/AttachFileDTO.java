package basic.board.attachFile;

public class AttachFileDTO {
    private Long attachFileNo;
    private Long galleryNo;
    private String fileName;
    private String pathName;

    public AttachFileDTO(Long attachFileNo, Long galleryNo, String fileName, String pathName) {
        this.attachFileNo = attachFileNo;
        this.galleryNo = galleryNo;
        this.fileName = fileName;
        this.pathName = pathName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
