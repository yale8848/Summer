package ren.yale.java.method;

import ren.yale.java.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yale on 2018/1/31.
 */
public class ClassInfo {

    private String classPath="";

    private Object clazzObj;

    private Class clazz;
    private Interceptor[] befores;

    private List<MethodInfo> methodInfoList = new ArrayList<>();


    public Interceptor[] getBefores() {
        return befores;
    }

    public void setBefores(Interceptor[] befores) {
        this.befores = befores;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }


    public Object getClazzObj() {
        return clazzObj;
    }

    public void setClazzObj(Object clazzObj) {
        this.clazzObj = clazzObj;
    }
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
