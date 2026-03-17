package com.deevyanshu.git_blog_pipeline.Utilities;

public class GithubUtil {

    public static String[] extract(String url) {

        String clean = url.replace("https://github.com/","");

        String[] parts = clean.split("/");
        parts[1]=parts[1].replace(".git","");

        return new String[]{parts[0], parts[1]};
    }
}
