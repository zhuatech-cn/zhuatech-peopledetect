/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.peopledetect.controller;

import cn.zhuatech.peopledetect.service.PeopleDetectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/peopledetect")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class PeopleDetectController {
    private final PeopleDetectService service;
    public PeopleDetectController(PeopleDetectService service) { this.service = service; }
    @PostMapping("/analyze") public PeopleDetectService.Result analyze(@Valid @RequestBody PeopleDetectService.Request request) { return service.analyze(request); }
}
