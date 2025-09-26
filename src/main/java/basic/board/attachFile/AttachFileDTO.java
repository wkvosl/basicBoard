package basic.board.attachFile;

public class AttachFileDTO {
    private Long attachFileNo;
    private String fileName;
    private String pathName;


    public Long getAttachFileNo() {
        return attachFileNo;
    }

    public void setAttachFileNo(Long attachFileNo) {
        this.attachFileNo = attachFileNo;
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
