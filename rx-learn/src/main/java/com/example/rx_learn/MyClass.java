package com.example.rx_learn;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyClass {
  public static final String TAG = "LIUKE";
//  public static CopyOnWriteArrayList<String> sl = new CopyOnWriteArrayList<>();
  public static ArrayList<String> sl = new ArrayList<>();

  public static void main(String [] args){
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");
    sl.add("1");


    testit();
    add();
    testit();
    add();
    testit();
    add();
    testit();
    add();
    testit();
    add();
    add();
    testit();
    testit();
  }

  private   static void add(){
    synchronized (sl) {
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
      sl.add("1");
    }
  }
  private  synchronized static void testit(){
    final ArrayList<String>  ar = new ArrayList<>();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (sl) {
          for (String s : sl) {
            ar.add(s);
          }
        }
      }
    });

    thread.start();

  }

  private static void testRx2(){

    //观察者
    Observer<String> observer = new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {
        //注册线程
        System.out.println("observer-onSubscribe: ");
      }

      @Override
      public void onNext(String s) {
        System.out.println("observer-OnNext : " + s);

      }

      @Override
      public void onError(Throwable e) {
        System.out.println("observer-onError : ");


      }

      @Override
      public void onComplete() {
        System.out.println("observer-onComplete: ");

      }
    };


    //被观察者
    Observable observable = Observable.create(new ObservableOnSubscribe<String>(){

      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("Hello");
        emitter.onNext("Hi");
        emitter.onNext("Rxjava2");
      }
    });

    Observable observable1 = Observable.just("a","b","c");
    Observable observable2 = Observable.fromArray("aa","b","c");


    //被观察者 -> 观察者 订阅
    observable1.subscribe(observer);

  }

}
