# Summer

Summer is a web server which connect JAX-RS and Vertx

## Add to pom

 ```xml

<dependency>
  <groupId>ren.yale.java</groupId>
  <artifactId>summer</artifactId>
  <version>1.0.3</version>
</dependency>

```

## How to use

```java

  SummerServer summerServer =SummerServer.create(8080);

  summerServer.getSummerRouter().registerResource(Hello.class);

  summerServer.start();

```

hello.java

```java

@Path("/hello")
@Before(TestInterceptor.class)
public class Hello {

    @GET
    @Path("/h1/{id}")
    @Produces({MediaType.TEXT_HTML})
    public String h1(@Context RoutingContext routingContext,
                     @PathParam("id") String id,
                     @DefaultValue("2") @QueryParam("step") int step, String text, @Context HttpServerRequest request,
                     @Context HttpServerResponse response){

        return "<html><body>bbcc</body></html>";
    }

    @GET
    @Path("/h2")
    public Test h2(){
        return new Test();
    }

    @GET
    @Path("/h3")
    public void h3(@Context HttpServerResponse response, @Context Vertx vertx){
        response.end("<html><body>bb</body></html>");
    }

    @GET
    @Path("/xml1")
    @Produces({MediaType.TEXT_XML})
    public Test xml1(){
        return new Test();
    }

    @GET
    @Path("/xml2")
    @Produces({MediaType.APPLICATION_XML})
    public Test xml2(){
        return new Test();
    }

}


```

## Add resource

Like Hello.java, you can create your own resource and then call `summerServer.getSummerRouter().registerResource(Hello.class);`

## AOP

- Create a intercepter implements Interceptor

```java

public class TestInterceptor implements Interceptor {
    
    @Override
    public boolean handle(RoutingContext routingContext) {
        return false;
    }
}

```
`if handle return true will interrupt the chain`


```java

@Path("/hello")
@Before(TestInterceptor.class)
public class Hello {}

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

By default each method will return Object will return json 

```java

    @GET
    @Path("/h2")
    public Test h2(){
        return new Test();
    }

```
this will return Test json object

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


