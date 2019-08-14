package com.study.xl.tymh.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ErrorPageController
 * @Description TODO
 * @Author xule
 * @Date 2019/8/12 14:13
 * @Version 1.0
 **/
@RequestMapping("error")
@Controller
public class ErrorPageController {

    @GetMapping("404")
    public String notFoundView() {
        return "404";
    }
    @GetMapping("500")
    public String serverErrorView() {
        return "500";
    }
    @GetMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
