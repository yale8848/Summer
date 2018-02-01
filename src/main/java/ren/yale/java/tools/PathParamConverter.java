package ren.yale.java.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Yale
 * Convert JAX-RS path param /{id}/  to Vertx-Web path param pattern /:id/
 * @create 2018-02-01 9:49
 **/
public class PathParamConverter {

    public static String converter(String path){
        if (path==null||path.length()==0){
            return path;
        }
        Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(path);
        while (matcher.find()){

            String p = matcher.group(0);
            if (p.length()>0){
                p = p.replace("{","").replace("}","");
                path=path.replace(matcher.group(0),":"+p);
            }

        }
        return path;

    }
}
