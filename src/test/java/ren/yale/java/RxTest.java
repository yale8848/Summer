package ren.yale.java;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Yale
 * create at: 2018-02-05 9:27
 **/
public class RxTest {

    private static void log(String text){
        System.out.println(text);
    }
    public static void main(String args[]){



        Observable<Integer>  observable1 = Observable.create(observableEmitter -> {
            log("observable1 onNext ready");
            observableEmitter.onNext(1);
            log("observable1 onNext onNext  1");
            observableEmitter.onComplete();
            log("observable1 onComplete");
        });
        Observable<Integer>  observable2 = Observable.create(observableEmitter -> {
            log("observable2 onNext ready");
            if(1/0 ==0){
                observableEmitter.onNext(2);
                log("observable2 onNext onNext  1");
            }else{
                log("observable2 onNext onNext  1");
                observableEmitter.onNext(3);
            }
            observableEmitter.onComplete();
        });
        observable2.delay(2, TimeUnit.SECONDS);

        Observable.zip(observable1,observable2,(int1,int2) ->{
            return ""+int1+" "+int2;
        }).subscribe(str->{
            log(str);
        },throwable -> {
            log("error: "+throwable.getMessage());
        });


    }
}
