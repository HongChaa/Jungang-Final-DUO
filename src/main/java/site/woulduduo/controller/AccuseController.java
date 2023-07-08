package site.woulduduo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.woulduduo.dto.request.accuse.UserAccuseRequestDTO;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.accuse.AccuseListResponseDTO;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.entity.Accuse;
import site.woulduduo.enumeration.AdminViewType;
import site.woulduduo.enumeration.Role;
import site.woulduduo.service.AccuseService;

import javax.servlet.http.HttpSession;

import static site.woulduduo.util.LoginUtil.LOGIN_KEY;

@RestController
@Slf4j
@RequiredArgsConstructor


    public class AccuseController {

    private final AccuseService accuseService;

    @GetMapping("/api/v1/user/accuse")
    public ResponseEntity<?> getAccuseListByAdmin(HttpSession session,
                                                  @PathVariable AdminViewType type,
                                                  @PathVariable int page){
        if (!((LoginUserResponseDTO) session.getAttribute(LOGIN_KEY)).getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageDTO dto = PageDTO.builder().page(page).build();

        ListResponseDTO<AccuseListResponseDTO, Accuse> accuseListByAdmin = accuseService.getAccuseListByAdmin(dto);
        return ResponseEntity
                .ok()
                .body(accuseListByAdmin);
    }

    //금일 신고 유저 목록 list
    @GetMapping("/api/v1/user/accuse1")
    public ResponseEntity<?> getTodayAccuseListByAdmin(HttpSession session,
                                                  PageDTO dto){
        log.info("{}dtozz",dto);
        ListResponseDTO<AccuseListResponseDTO, Accuse> accuseListByAdmin = accuseService.getAccuseTodayListByAdmin(dto);
        log.info("{}accuseListByAdmin",accuseListByAdmin);


        return ResponseEntity
                .ok()
                .body(accuseListByAdmin);
    }

//모달 데이터 저장
    @PostMapping("/user/accuse")
    public boolean accuseUser(HttpSession session, @RequestBody UserAccuseRequestDTO dto){
        log.info("{}dto123===========",dto);
        boolean b = accuseService.accuseUser(dto);
        System.out.println("btruefalse = " + b);
        return b;
    }

    @PostMapping("/user/accuse/count")
    @ResponseBody
    public ResponseEntity<?> accuseUserCount(HttpSession session, @RequestBody UserAccuseRequestDTO dto){
        log.info("{}123asd123===========",dto);
       int size = accuseService.accuseUserCount(dto);
        System.out.println("size = " + size);
        return ResponseEntity
                .ok()
                .body(size);
    }
}
