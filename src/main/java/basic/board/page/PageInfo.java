package basic.board.page;

public class PageInfo {
        private int number;             // 현재 페이지 번호 (0부터 시작)
        private int size;               // 페이지당 아이템 수
        private int totalPages;         // 전체 페이지 수
        private long totalElements;     // 전체 아이템 수
        private int numberOfElements;   // 현재 페이지의 아이템 수
        private boolean first;          // 첫 페이지 여부
        private boolean last;           // 마지막 페이지 여부
        private boolean empty;          // 비었는지 여부
        private long boardTotal;        // 총 게시물

    public PageInfo() {
    }

    public PageInfo(int number, int size, int totalPages, long totalElements,
                    int numberOfElements, boolean first, boolean last, boolean empty,
                    long boardTotal) {
            this.number = number;
            this.size = size;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.numberOfElements = numberOfElements;
            this.first = first;
            this.last = last;
            this.empty = empty;
            this.boardTotal = boardTotal;
        }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Long getBoardTotal() {
        return boardTotal;
    }

    public void setBoardTotal(Long boardTotal) {
        this.boardTotal = boardTotal;
    }
}
