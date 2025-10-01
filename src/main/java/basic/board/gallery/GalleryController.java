package basic.board.gallery;

import basic.board.page.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class GalleryController {


    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    //저장 및 업데이트
    @PostMapping("/gallery/save")
    public GalleryEntity save(@RequestBody GalleryEntity galleryEntity) {
        if(galleryEntity.getGalleryNo() != null){
            galleryEntity.setLastUpdateDate(LocalDateTime.now());
        }else{
            galleryEntity.setRegDate(LocalDateTime.now());
        }
        return galleryService.save(galleryEntity);
    }
    //상세
    @GetMapping("/gallery/{id}")
    public GalleryWithFileDTO findById(@PathVariable Long id) {
        return galleryService.findById(id);

    }
    //전체목록
    @GetMapping("/gallery/list")
    public GalleryPageResponseDTO<GalleryWithFileDTO> getBoardPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String category
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("galleryNo").descending());
        Page<GalleryWithFileDTO> galleryPage = galleryService.findAllBySearch(pageable, category, search);

        PageInfo pageInfo = new PageInfo(
                galleryPage.getNumber()+1,
                galleryPage.getSize(),
                galleryPage.getTotalPages(),
                galleryPage.getTotalElements(),
                galleryPage.getNumberOfElements(),
                galleryPage.isFirst(),
                galleryPage.isLast(),
                galleryPage.isEmpty(),
                galleryService.countGalleryAllNotDeleted()
        );
        return new GalleryPageResponseDTO<>(galleryPage.getContent(), pageInfo);
    }

    @PostMapping("/gallery/delete")
    public ResponseEntity<String> deleteById(@RequestBody GalleryEntity galleryEntity) {

        try{

            galleryService.deleteById(galleryEntity);
            return ResponseEntity.ok("삭제 완료");

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
