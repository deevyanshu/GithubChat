package com.deevyanshu.git_blog_pipeline.Service;

import com.deevyanshu.git_blog_pipeline.Utilities.GithubUtil;
import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubService {


    @Autowired
    private PineconeVectorStore vectorStore;

    public String getRepoCode(String url) throws IOException {
        String[] parts= GithubUtil.extract(url);

        String owner=parts[0];
        String repo=parts[1];


        GitHub gitHub=GitHub.connectAnonymously();

        GHRepository ghRepository=gitHub.getRepository(owner+"/"+repo);
        System.out.println(ghRepository.getOwnerName());

        StringBuilder code=new StringBuilder();

        GHTree tree=ghRepository.getTreeRecursive("main",1);

//        for(GHContent file:ghRepository.getDirectoryContent("/"))
//        {
//            if(file.getName().endsWith(".xml") ||
//                    file.getName().endsWith(".md") ||
//                    file.getName().endsWith(".py")) {
//
//                code.append(file.read().toString());
//                code.append("\n");
//            }
//        }

        List<Document> documents=new ArrayList<>();
        for(GHTreeEntry entry:tree.getTree())
        {
            if("blob".equals(entry.getType()) && (entry.getPath().endsWith("xml") || entry.getPath().endsWith("java")
             || entry.getPath().endsWith("py") || entry.getPath().endsWith("js") || entry.getPath().endsWith("Html") ||
                    entry.getPath().endsWith("ts") || entry.getPath().endsWith("jsx") || entry.getPath().endsWith("tsx")))
            {
                GHBlob blob=ghRepository.getBlob(entry.getSha());
                InputStream is=blob.read();
                Document doc=new Document(code.append(new String(is.readAllBytes(), StandardCharsets.UTF_8)).toString());
                documents.add(doc);

            }else if("tree".equals(entry.getType()))
            {
                System.out.println("Path is: "+entry.getPath());
            }
        }
        return loadData(transform(documents,url));
    }

    public List<Document> transform(List<Document> documents,String url)
    {
        TextSplitter splitter=new TokenTextSplitter();
        return splitter.transform(documents).stream().map(doc->{
            Map<String,Object> metadata=new HashMap<>();
            metadata.put("repo",url);
            return new Document(doc.getText(),metadata);
        }).collect(Collectors.toList());
    }

    public String loadData(List<Document> documents)
    {
        vectorStore.add(documents);
        return "Added";
    }


}
