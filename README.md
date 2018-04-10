# Summer

Vertx router with JAX-RS

## Add to pom

 ```xml

<dependency>
  <groupId>ren.yale.java</groupId>
  <artifactId>summer</artifactId>
  <version>1.1.8</version>
</dependency>

```

## How to use

Use SummerRouter:

```java

    public class WebServer extends AbstractVerticle {

        @Override
        public void start() throws Exception {
            Router router = Router.router(vertx);
            SummerRouter summerRouter =  new SummerRouter(router,vertx);
            summerRouter.registerResource(Hello.class);
            vertx.createHttpServer()
                    .requestHandler(router::accept)
                    .listen(port,host,httpServerAsyncResult -> {
                        if (httpServerAsyncResult.succeeded()){
                            System.out.println("listen at: http://"+host+":"+port);
                        }else{
                            System.out.println(httpServerAsyncResult.cause().getCause());
                        }
                    });

        }
    }
```

Use simple SummerServer(use io.vertx.core.AbstractVerticle):

```java

  SummerServer summerServer =SummerServer.create(8080);

  summerServer.getSummerRouter().registerResource(Hello.class);

  summerServer.start();

```
or further:

```java

   VertxOptions options = new VertxOptions();
   options.setBlockedThreadCheckInterval(20000);
   options.setMaxEventLoopExecuteTime(20000);
   
   SummerServer summerServer =SummerServer.create("localhost",8080,options);

   summerServer.getVertx().
                deployVerticle(MyVerticle.class.getName());
   
   summerServer.getSummerRouter().registerResource(Hello.class);
    
   DeploymentOptions deploymentOptions = new DeploymentOptions();
   deploymentOptions.setWorker(true);
   summerServer.start(deploymentOptions);


```


Hello.java with JAX-RS

```java

@Path("/hello")
@Before(LogInterceptor.class)
public class Hello {

    @GET
    @Path("/html/{name}")
    @Produces({MediaType.TEXT_HTML})
    public String h1(@Context RoutingContext routingContext,
                     @PathParam("name") String name,
                     @DefaultValue("18") @QueryParam("age") int age, String text){
        return String.format("<html><body>name:%s,age:%d</body></html>",name,age);
    }

    @POST
    @Path("/name/bob")
    @Produces({MediaType.APPLICATION_JSON})
    public User h2(@QueryParam("age") int age,@FormParam("name") String name) {
        User u = new User();
        u.setName(name);
        u.setAge(age);
        return u;
    }

    @GET
    @Path("/async")
    public void h3(@Context HttpServerResponse response, @Context Vertx vertx){

        vertx.eventBus().send("user",EventMessage.message("bob").setKey("name"),message->{
            EventMessage eventMessage = (EventMessage) message.result().body();
           if (eventMessage.isSuccess()){
               String ret= String.format("name:%s,age:%d",eventMessage.getMessage(),18);
               response.end(ret);
          }else{
               response.end("error");
           }
        });

    }

    @GET
    @Path("/xml")
    @Produces({MediaType.TEXT_XML})
    public User xml(){
        return new User();
    }

    @GET
    @Path("/aop")
    @Produces({MediaType.APPLICATION_JSON})
    @After(ChangeUserInterceptor.class)
    public User getInter(){
        User u = new User();
        u.setName("bob");
        u.setAge(18);
        return u;
    }
   
    @HEAD
    @Path("/head")
    public void head(@Context RoutingContext routingContext){
        routingContext.response().setStatusCode(200).end();
    }
    
    @PUT
    @Path("/put/bob/{age}")
    @Produces({MediaType.APPLICATION_JSON})
    public User put(@Context RoutingContext routingContext,@PathParam("age") int age){

        User u = new User();
        u.setName("bob");
        u.setAge(age);
        return u;
    }

    @DELETE
    @Path("/delete/{name}")
    public String delete(@Context RoutingContext routingContext, @PathParam("name") String name){
        return "delete "+name+" success";
    }
}

```

## Add resource

Like Hello.java, you can create your own resource and then call `summerServer.getSummerRouter().registerResource(Hello.class);`

## AOP

- Create a intercepter implements Interceptor. @After interceptor only work in sync mode

```java

public class LogInterceptor implements Interceptor {
    @Override
    public boolean handle(RoutingContext routingContext, Object obj) {
        System.out.println(routingContext.request().absoluteURI());
        return false;
    }
}

public class ChangeUserInterceptor implements Interceptor {
    @Override
    public boolean handle(RoutingContext routingContext,Object obj) {
        User user = (User) obj;
        user.setName("Alice");
        routingContext.response()
                .end(JsonObject.mapFrom(user).encodePrettily());
        return true;
    }
}

```
`if handle return true will interrupt the chain, the method interceptor work before class interceptor`


```java

@Path("/hello")
@Before(LogInterceptor.class)
public class Hello {}

@After(ChangeUserInterceptor.class)
public User getInter(){}

```

## Inject object 

```java


    @GET
    @Path("/test")
    public void test(@Context RoutingContext routingContext,
                     @Context HttpServerRequest request,
                     @Context HttpServerResponse response,
                     @Context Session session,
                     @Context Vertx vertx
                       ){

    }

```

## Return json

By default each method will return Object will return json;

```java

    @GET
    @Path("/h2")
    public Test h2(){
        return new Test();
    }

```
this will return Test json object

## SQL builder

SummerSQL [same as mybatis3 sqlbuilder](http://www.mybatis.org/mybatis-3/statement-builders.html)

```java
// With conditionals (note the final parameters, required for the anonymous inner class to access them)
public String selectPersonLike(final String id, final String firstName, final String lastName) {
  return new SummerSQL() {{
    SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FIRST_NAME, P.LAST_NAME");
    FROM("PERSON P");
    if (id != null) {
      WHERE("P.ID like #{id}");
    }
    if (firstName != null) {
      WHERE("P.FIRST_NAME like #{firstName}");
    }
    if (lastName != null) {
      WHERE("P.LAST_NAME like #{lastName}");
    }
    ORDER_BY("P.LAST_NAME");
  }}.toString();
}


```

## SQL ResultMapper

```java

   sqlConnection.query(new SummerSQL().SELECT("*")
                                .FROM("db_test.tb_test").toString(), resultSetAsyncResult -> {
                            if (resultSetAsyncResult.succeeded()){

                               List<DBTest> tests =  ResultSetMapper.create().camelName()
                                        .mapperList(resultSetAsyncResult.result(), DBTest.class);
                            }
                            sqlConnection.close();
                 });


```


## License

```
MIT License

Copyright (c) 2018 Yale

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```


