package com.deevyanshu.git_blog_pipeline.Controller;

import com.deevyanshu.git_blog_pipeline.Service.AiService;
import com.deevyanshu.git_blog_pipeline.Service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class GithubController {

    private GithubService githubService;
    private AiService aiService;

    public GithubController(GithubService githubService, AiService aiService) {
        this.githubService = githubService;
        this.aiService = aiService;
    }

    @PostMapping("/load")
    public ResponseEntity<String> load(@RequestParam(value = "q",required = true) String url) throws IOException {
        String code=githubService.getRepoCode(url);
        System.out.println("code is:  "+code);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q",required = true) String ques,
                                       @RequestParam(value = "w", required = true) String url)
    {
        return ResponseEntity.ok(this.aiService.chat(ques,url));
    }
}
