package ren.yale.java.method;

import ren.yale.java.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Yale
 *
 * create at:  2018-01-31 17:46
 **/
public class MethodInfo {

    private Class httpMethod;

    private String methodPath;

    private String producesType="*/*";

    private Method method;
    private List<ArgInfo> argInfoList = new ArrayList<>();
    private Interceptor[] befores;

    private boolean isBlocking;
    public boolean isBlocking() {
        return isBlocking;
    }
    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    public Interceptor[] getBefores() {
        return befores;
    }

    public void setBefores(Interceptor[] befores) {
        this.befores = befores;
    }

    public String getProducesType() {
        return producesType;
    }

    public void setProducesType(String producesType) {
        this.producesType = producesType;
    }

    public String getMethodPath() {
        return methodPath;
    }

    public void setMethodPath(String methodPath) {
        this.methodPath = methodPath;
    }


    public Class getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(Class httpMethod) {
        this.httpMethod = httpMethod;
    }
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<ArgInfo> getArgInfoList() {
        return argInfoList;
    }

    public void addArgInfo(ArgInfo argInfo) {
       argInfoList.add(argInfo);
    }
}
