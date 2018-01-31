package ren.yale.java.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Yale
 *
 * @create 2018-01-31 17:55
 **/
public class ArgInfo {

    private Annotation annotation;
    private Parameter parameter;
    private Class aClass;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
