package ren.yale.java.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Yale
 *
 * @create 2018-01-31 17:55
 **/
public class ArgInfo {

    private Annotation annotation[];
    private Parameter parameter;
    private Class clazz;

    private String defaultValue;
    private boolean isContext;

    private boolean isPathParam;
    private String pathParam;

    private boolean isQueryParam;
    private String queryParam;

    private boolean isFormParam;
    private String formParam;



    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isQueryParam() {
        return isQueryParam;
    }

    public void setQueryParam(boolean queryParam) {
        isQueryParam = queryParam;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public boolean isFormParam() {
        return isFormParam;
    }

    public void setFormParam(boolean formParam) {
        isFormParam = formParam;
    }

    public String getFormParam() {
        return formParam;
    }

    public void setFormParam(String formParam) {
        this.formParam = formParam;
    }

    public String getPathParam() {
        return pathParam;
    }

    public void setPathParam(String pathParam) {
        this.pathParam = pathParam;
    }

    public boolean isPathParam() {
        return isPathParam;
    }

    public void setPathParam(boolean pathParam) {
        isPathParam = pathParam;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }



    public boolean isContext() {
        return isContext;
    }

    public void setContext(boolean context) {
        isContext = context;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }


}
