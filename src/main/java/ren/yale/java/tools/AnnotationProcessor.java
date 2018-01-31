package ren.yale.java.tools;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Yale
 *
 * @create 2018-01-31 17:25
 **/
public class AnnotationProcessor {

    private AnnotationProcessor() {
    }

    public static void get(List<ClassInfo> classInfos,Class clazz) {

        if (!isRestClass(clazz)){
            return ;
        }
        Path path = (Path) clazz.getAnnotation(Path.class);

        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassPath(path.value());

        for (Method method : clazz.getMethods()) {

            Path pathMthod = (Path) method.getAnnotation(Path.class);

            Parameter[] parameters = method.getParameters();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();

            int i=0;
            for (Annotation[] an:annotations) {
                String p = path.value()+(pathMthod!=null?pathMthod.value():"");
                if (an.length>0){
                    MethodInfo methodInfo = new MethodInfo();
                    ArgInfo argInfo = new ArgInfo();
                    Annotation one = an[0];
                    if (one instanceof Context){
                        argInfo.setAnnotation(one);
                        argInfo.setaClass(parameterTypes[i]);
                        argInfo.setParameter(parameters[i]);
                    }
                    if (argInfo.getAnnotation()!=null){
                        methodInfo.addArgInfo(argInfo);
                    }
                    classInfo.addMethodInfo(methodInfo);
                }

                i++;

            }

        }
    }
    private static boolean isRestClass(Class cls) {

        List<Class<? extends Annotation>> search = Arrays.asList(Path.class);

        for (Class<? extends Annotation> item: search) {
            if (cls.getAnnotation(item) != null) {
                return true;
            }
        }

        return false;
    }
    private static boolean isRestMethod(Method method) {

        List<Class<? extends Annotation>> search = Arrays.asList(Path.class,
                HttpMethod.class,
                GET.class,
                POST.class,
                PUT.class,
                DELETE.class,
                OPTIONS.class,
                HEAD.class);

        for (Class<? extends Annotation> item: search) {
            if (method.getAnnotation(item) != null) {
                return true;
            }
        }

        return false;
    }
}
