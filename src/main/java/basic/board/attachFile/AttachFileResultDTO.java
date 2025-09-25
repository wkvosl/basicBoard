package basic.board.attachFile;

public class AttachFileResultDTO {

    private Long fileId;
    private boolean success;

    public AttachFileResultDTO(Long fileId, boolean success) {
        this.fileId = fileId;
        this.success = success;
    }

    public AttachFileResultDTO(Long fileId) {
        this.fileId = fileId;
    }

    public AttachFileResultDTO(boolean success) {
        this.success = success;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
