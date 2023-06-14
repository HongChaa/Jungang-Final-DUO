package site.woulduduo.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.woulduduo.dto.request.page.AdminSearchType;
import site.woulduduo.dto.request.user.UserCommentRequestDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.user.UsersByAdminResponseDTO;
import site.woulduduo.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 마이페이지 - 프로필 카드 등록페이지 열기
    @GetMapping("/user/register-duo")
    public String registerDUO(/*HttpSession session, */Model model) {

        return "my-page/mypage-duoprofile";
    }
    @PostMapping("/user/register-duo")
    public String registerDUO(/*HttpSession session, */UserCommentRequestDTO dto) {

        boolean b = userService.registerDUO(/*session, */dto);
        log.info("프로필카드등록 성공여부 : {}", b);
        log.info("@@@@dto@@@@ :{}", dto);

        return "redirect:/user/register-duo";
    }

    @GetMapping("/user/admin")
    //관리자 페이지 열기
    public String showAdminpage(HttpSession session, Model model){

        return "admin/admin";
    }

    //관리자 페이지 리스트 가져오기
    public ResponseEntity<?> getUserListByAdmin(AdminSearchType type){
        ListResponseDTO<UsersByAdminResponseDTO>
                userListByAdmin = userService.getUserListByAdmin(type);


        return ResponseEntity
                .ok()
                .body(userListByAdmin);
    }

//    @GetMapping("/user/detail/admin")
//    //관리자 페이지 자세히 보기
//    public String showDetailByAdmin(HttpSession session,Model model, String userAccount){
//
//        return "";
//    }
//
//    @GetMapping("/user/ban")
//    public String changeBanStatus(HttpSession session, String userAccount){
//
//        return "redirect";
//    }
//
//    @GetMapping("/user/duo")
//    public String showDetailUser(HttpSession session, String userAccount){
//
//        return "";
//    }
}
