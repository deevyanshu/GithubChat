package com.deevyanshu.git_blog_pipeline.Controller;

import com.deevyanshu.git_blog_pipeline.Service.AiService;
import com.deevyanshu.git_blog_pipeline.Service.GithubService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("https://magical-kringle-1f1391.netlify.app")
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

    @PostMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam(value = "q",required = true) String ques,
                             @RequestParam(value = "w", required = true) String url)
    {
        return this.aiService.streamResponse(ques,url);
    }




}
