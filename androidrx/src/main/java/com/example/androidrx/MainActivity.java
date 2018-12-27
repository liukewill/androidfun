package com.example.androidrx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidrx.aes.AESUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.DexClassLoader;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidrx.aes.AESUtil.R_NAME;

public class MainActivity extends Activity {
  public static final String TAG = "TAG";

  @Bind(R.id.tv)
  TextView textView;

  @Bind(R.id.btn)
  Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn)
  void click(){
    testAES();
  }

  void testAES(){
    final String clsName = AESUtil.decrypt("466766766676597E15856FC9", "");
    textView.setText(clsName);
  }




  /**
   * Obervable 被观察者 生产侧
   * Observer  观察者 消费侧
   *
   * subscribeOn 生产侧线程，默认当前线程，
   * Schedulers.newThread() 新子线程
   * Schedulers.io()  （读写文件、读写数据库、网络信息交互等）无数量上限的线程池，可以重用空闲的线程  io() 比 newThread() 更有效率
   *
   */
  private void initRx() {

    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        emitter.onNext(1);
        Thread.sleep(1000);
        emitter.onNext(2);
        Thread.sleep(1000);
        emitter.onNext(3);
        Thread.sleep(6000);
        emitter.onComplete();
      }
    }).map(new Function<Integer, Integer>() {

      @Override
      public Integer apply(Integer integer) throws Exception {
        return integer *10;
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new io.reactivex.Observer<Integer>() {

          Disposable disposable;
          @Override
          public void onSubscribe(Disposable d) {
            this.disposable = d;
          }

          @Override
          public void onNext(Integer integer) {
            textView.setText(integer + "");
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {
            textView.setText("OK");
          }
        });
  }

  private void initZip(){
    Observable.zip(getObStr(), getObString(), new BiFunction<Integer, String, String>() {
      @Override
      public String apply(Integer integer, String s) throws Exception {
        return s+integer;
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
      @Override
      public void accept(String s) throws Exception {
        textView.setText(s);
      }
    });
  }

  private Observable<String> getObString(){
    return Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        doOb(emitter);

      }
    });
  }

  private void doOb(ObservableEmitter<String> emitter) throws InterruptedException {
    Thread.sleep(1000);
    emitter.onNext("A");
    Thread.sleep(5000);
    emitter.onNext("B");
    Thread.sleep(1000);
    emitter.onNext("C");
    emitter.onNext("D");
    emitter.onNext("E");
    emitter.onNext("F");
  }

  private Observable<Integer> getObStr(){
    return  Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        Thread.sleep(100);
        emitter.onNext(1);
        Thread.sleep(100);
        emitter.onNext(2);
        Thread.sleep(100);
        emitter.onNext(3);

        emitter.onComplete();
      }
    });
  }

}


