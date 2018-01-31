package ren.yale.java.tools;

import javax.naming.Context;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Yale
 *
 * @create 2018-01-31 17:46
 **/
public class MethodInfo {

    private Annotation httpMethod;
    private Method method;
    private List<ArgInfo> argInfoList = new ArrayList<>();

    public Annotation getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(Annotation httpMethod) {
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
        this.argInfoList.add(argInfo);
    }
}
