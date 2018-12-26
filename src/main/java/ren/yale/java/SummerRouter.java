package ren.yale.java;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ren.yale.java.interceptor.Interceptor;
import ren.yale.java.method.ArgInfo;
import ren.yale.java.method.ClassInfo;
import ren.yale.java.method.MethodInfo;
import ren.yale.java.tools.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Yale
 *
 * create at:  2018-02-01 14:08
 **/
public class SummerRouter {
    private final static Logger LOGGER = LogManager.getLogger(SummerRouter.class.getName());

    private List<ClassInfo> classInfos;
    private Router router;
    private Vertx vertx;
    private String contextPath="";
    public SummerRouter(Router router,Vertx vertx){
        this.router = router;
        this.vertx =vertx;
        this.classInfos = new ArrayList<>();

        this.init();
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        if (!StringUtils.isEmpty(contextPath)){
            this.contextPath = contextPath;
        }
    }
    public Router getRouter() {
        return router;
    }
    private void init(){
        router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());
        SessionHandler handler = SessionHandler.create(LocalSessionStore.create(vertx));
        handler.setNagHttps(true);
        router.route().handler(handler);
    }
    private boolean isRegister(Class clazz){

        for (ClassInfo classInfo:classInfos) {
            if (classInfo.getClazz() == clazz){
                return true;
            }
        }
        return false;
    }



    public void registerResource(Class clazz){

        if (isRegister(clazz)){
            return;
        }
        ClassInfo classInfo = MethodsProcessor.get(classInfos, clazz);
        if (classInfo!=null){
            for (MethodInfo methodInfo:classInfo.getMethodInfoList()) {
                String p = classInfo.getClassPath()+methodInfo.getMethodPath();
                p = PathParamConverter.converter(p);
                p =addContextPath(p);
                Route route=null;
                if (methodInfo.getHttpMethod()== null){
                    route = router.route(p);
                }else
                if (methodInfo.getHttpMethod()== GET.class){
                    route = router.get(p);
                }else if (methodInfo.getHttpMethod()== POST.class){
                    route = router.post(p);
                }else if (methodInfo.getHttpMethod()== PUT.class){
                    route = router.put(p);
                }else if (methodInfo.getHttpMethod()== DELETE.class){
                    route = router.delete(p);
                }else if (methodInfo.getHttpMethod()== OPTIONS.class){
                    route = router.options(p);
                }else if (methodInfo.getHttpMethod()== HEAD.class){
                    route = router.head(p);
                }
                if (methodInfo.isBlocking()){
                    route.blockingHandler(getHandler(classInfo,methodInfo));
                }else{
                    route.handler(getHandler(classInfo,methodInfo));
                }
            }
        }
    }

    private String addContextPath(String path) {

        return contextPath+path;
    }

    private Object covertType(Class type,String v) throws Exception{


        String typeName = type.getTypeName();

        if (type == String.class){
            return v;
        }
        if (type == Integer.class||typeName.equals("int")){
            return Integer.parseInt(v);
        }
        if (type == Long.class||typeName.equals("long")){
            return Long.parseLong(v);
        }
        if (type == Float.class||typeName.equals("float")){
            return Float.parseFloat(v);
        }
        if (type == Double.class||typeName.equals("double")){
            return Double.parseDouble(v);
        }

        return null;

    }
    private Object getPathParamArg(RoutingContext routingContext, ArgInfo argInfo){

        try {
            String path = routingContext.request().getParam(argInfo.getPathParam());
            if (!StringUtils.isEmpty(path)){
                return covertType(argInfo.getClazz(),path);
            }
            if (!StringUtils.isEmpty(argInfo.getDefaultValue())){
                return covertType(argInfo.getClazz(),argInfo.getDefaultValue());
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        return null;

    }
    private Object getFromParamArg(RoutingContext routingContext,ArgInfo argInfo){

        try {
            String q = routingContext.request().getParam(argInfo.getFormParam());
            if (!StringUtils.isEmpty(q)){
                return covertType(argInfo.getClazz(),q);
            }
            if (!StringUtils.isEmpty(argInfo.getDefaultValue())){
                return covertType(argInfo.getClazz(),argInfo.getDefaultValue());
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        return null;
    }
    private Object getQueryParamArg(RoutingContext routingContext,ArgInfo argInfo){

        try {
            String q = routingContext.request().getParam(argInfo.getQueryParam());
            if (!StringUtils.isEmpty(q)){
                return covertType(argInfo.getClazz(),q);
            }
            if (!StringUtils.isEmpty(argInfo.getDefaultValue())){
                return covertType(argInfo.getClazz(),argInfo.getDefaultValue());
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    private Object getContext(RoutingContext routingContext,ArgInfo argInfo){
        Class clz = argInfo.getClazz();
        if (clz ==RoutingContext.class){

            return routingContext;
        }else if (clz == HttpServerRequest.class){
            return routingContext.request();
        }else if (clz == HttpServerResponse.class){
            return routingContext.response();
        }else if (clz == Session.class){
            return routingContext.session();
        }else if (clz == Vertx.class){
            return vertx;
        }
        return null;
    }
    private Object[] getArgs(RoutingContext routingContext,ClassInfo classInfo,MethodInfo methodInfo){

        Object[] objects = new Object[methodInfo.getArgInfoList().size()];
        int i =0;
        for (ArgInfo argInfo:methodInfo.getArgInfoList()){

            if (argInfo.isContext()){
                objects[i] = getContext(routingContext,argInfo);
            }else if (argInfo.isQueryParam()){
                objects[i] = getQueryParamArg(routingContext,argInfo);
            }else if (argInfo.isFormParam()){
                objects[i] = getFromParamArg(routingContext,argInfo);
            }else if (argInfo.isPathParam()){
                objects[i] = getPathParamArg(routingContext,argInfo);
            }else{
                objects[i] = null;
            }
            i++;
        }

        return objects;

    }

    private String convert2XML(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(object, baos);
        return new String(baos.toByteArray());
    }

    private boolean handleBefores(RoutingContext routingContext,ClassInfo classInfo, MethodInfo methodInfo){
        List<Interceptor> beforeList = new ArrayList<>();

        if (methodInfo.getBefores()!=null){
            beforeList.addAll(Arrays.asList(methodInfo.getBefores()));
        }
        if (classInfo.getBefores()!=null){
            beforeList.addAll(Arrays.asList(classInfo.getBefores()));
        }
        for (Interceptor inter:beforeList) {
            if (inter.handle(routingContext,null)){
                return true;
            }
        }
        return false;
    }
    private boolean handleAfters(RoutingContext routingContext,ClassInfo classInfo, MethodInfo methodInfo
    ,Object obj){
        List<Interceptor> list = new ArrayList<>();

        if (methodInfo.getAfters()!=null){
            list.addAll(Arrays.asList(methodInfo.getAfters()));
        }
        if (classInfo.getAfters()!=null){
            list.addAll(Arrays.asList(classInfo.getAfters()));
        }
        for (Interceptor inter:list) {
            if (inter.handle(routingContext,obj)){
                return true;
            }
        }
        return false;
    }
    private void handlers(ClassInfo classInfo, MethodInfo methodInfo,RoutingContext routingContext){
        if (handleBefores(routingContext,classInfo,methodInfo)){
            return;
        }
        Object[] args = getArgs(routingContext,classInfo,methodInfo);
        routingContext.response().putHeader("Content-Type",methodInfo.getProducesType())
                .setStatusCode(200);

        try {
            Object result = methodInfo.getMethod().invoke(classInfo.getClazzObj(),args);
            if (result!=null&&result.getClass() != Void.class){
                if (!routingContext.response().ended()){

                    if( handleAfters(routingContext,classInfo,methodInfo,result)){
                        return;
                    }
                    if (!routingContext.response().ended()){
                        if (result instanceof  String){
                            routingContext.response().end((String) result);
                        }else{
                            if (methodInfo.getProducesType().indexOf(MediaType.TEXT_XML)>=0||
                                    methodInfo.getProducesType().indexOf(MediaType.APPLICATION_XML)>=0 ){
                                routingContext.response().end(convert2XML(result));
                            }else{
                                routingContext.response()
                                        .putHeader("Content-Type", MediaType.APPLICATION_JSON+";charset=utf-8").end(JsonObject.mapFrom(result).encodePrettily());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error(e.toString());
            routingContext.response().setStatusCode(500).putHeader("Content-Type", MediaType.TEXT_PLAIN+";charset=utf-8")
                    .end(e.toString());
        }
    }
    private Handler<RoutingContext> getHandler(ClassInfo classInfo, MethodInfo methodInfo){

        return (routingContext -> {

            try {

                handlers(classInfo,methodInfo,routingContext);

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                routingContext.response().setStatusCode(500).putHeader("Content-Type", MediaType.TEXT_PLAIN+";charset=utf-8")
                        .end(e.toString());
            }
        });
    }
}

