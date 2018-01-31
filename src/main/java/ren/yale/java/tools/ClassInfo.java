package ren.yale.java.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yale on 2018/1/31.
 */
public class ClassInfo {

    private String classPath="";

    private List<MethodInfo> methodInfoList = new ArrayList<>();


    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public List<MethodInfo> getMethodInfoList() {
        return methodInfoList;
    }

    public void addMethodInfo(MethodInfo methodInfo) {
        methodInfoList.add(methodInfo);
    }
}
