package com.example.rx;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * subscribeOn 工作线程 只能指定一次
 * observeOn 可指定多次
 */
public class MyClass {
  public static void main(String[] args) {
    merge();
  }

  public static void buffer() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .buffer(4, 3)
        .subscribe(new Consumer<List<Integer>>() {
          @Override
          public void accept(List<Integer> integers) throws Exception {
            System.out.println("" + integers.toString());
          }
        });
  }

  public static final void flatMap() {
    int[] a = {1, 2, 3};
    int[] b = {4, 5, 6};
    int[] c = {7, 8, 9};

    Observable.just(a, b, c)
        .flatMap(new Function<int[], ObservableSource<?>>() {
          @Override
          public ObservableSource<?> apply(int[] ints) throws Exception {
            return Observable.fromArray(ints);
          }
        });

  }

  public static final void merge(){
    Observable o1= Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        emitter.onError(new Exception());
        emitter.onNext(10);
        emitter.onNext(20);
      }
    });

    Observable o2= Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {

        Thread.sleep(3000);
        emitter.onNext(30);
      }
    });

    Observable.mergeDelayError(o1,o2)
        .subscribe(new Observer<Integer> (){
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Integer o) {
            System.out.println("onNext-"+o);
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("onNerror");

          }

          @Override
          public void onComplete() {
            System.out.println("OnComplete");
          }
        });
  }
}
