package site.woulduduo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/429")
    public String error429(HttpServletRequest request, RedirectAttributes ra) {
        log.warn("429 에러!!");
        ra.addFlashAttribute("url", request.getHeader("referer"));
        return "error/error429";
    }

}
