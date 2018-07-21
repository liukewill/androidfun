package com.example.rxjava;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "LIUKE";

  ImageView iv;
  TextView tv;
  TextView tv1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    iv = (ImageView) findViewById(R.id.home_iv);
    tv = findViewById(R.id.home_tv);
    tv1 = findViewById(R.id.home_tv1);

    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });


    testimage();

  }

  private void testRx2() {

    //观察者
    Observer<String> observer = new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {
        //注册线程
        Log.d(TAG, "observer-onSubscribe: ");
      }

      @Override
      public void onNext(String s) {
        Log.d(TAG, "observer-OnNext : " + s);
      }

      @Override
      public void onError(Throwable e) {
        Log.d(TAG, "observer-onError : ");

      }

      @Override
      public void onComplete() {
        Log.d(TAG, "observer-onComplete: ");
      }
    };


    //被观察者
    Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("Hello");
        emitter.onNext("Hi");
        emitter.onNext("Rxjava2");
        emitter.onComplete();
      }
    });

    //被观察者 -> 观察者  订阅
    observable.subscribe(observer);

  }

  private void testimage() {
    iv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        testFlatMap();
      }

    });
  }


  private void rxSetImageView() {
    final int drawableRes = R.mipmap.ic_launcher;

    //rxjava 默认 :哪个线程调用subscribe(),就在哪个线程发射生产事件，发射，在哪个线程发射，就在哪个线程消费
    //

    //时间发出和时间消费都在同一个线程
    Observable.create(new ObservableOnSubscribe<Drawable>() {
      @Override
      public void subscribe(ObservableEmitter<Drawable> emitter) throws Exception {
        Log.i(TAG, "subscribe() :" + Thread.currentThread().getName());
        Thread.sleep(3000);
        Drawable drawable = getDrawable(drawableRes);
        emitter.onNext(drawable);
        emitter.onComplete();
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Drawable>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Drawable drawable) {
            Log.i(TAG, "onNext() :" + Thread.currentThread().getName());
            iv.setImageDrawable(drawable);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

  private void testMap() {

    //map 使用function 事件对象变换，最常用的变换
    Observable.just(R.mipmap.ic_launcher)
        .subscribeOn(Schedulers.newThread())
        .map(new Function<Integer, Drawable>() {
          @Override
          public Drawable apply(Integer integer) throws Exception {
            Thread.sleep(1000);
            Drawable drawable = getDrawable(integer);
            return drawable;
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Drawable>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Drawable drawable) {
            iv.setImageDrawable(drawable);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

  private void testFlatMap() {
    final Student student1 = new Student();
    student1.name = "Mike";
    student1.courses = new String[]{"MATH", "SCIENCE", "HETH"};

    Student student2 = new Student();
    student2.name = "JACK";
    student2.courses = new String[]{"YUWEN", "SHUXU", "YINGYU"};

    final Student[] students = new Student[]{student1, student2};
    Observable.create(new ObservableOnSubscribe<Student>() {
      @Override
      public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
        for (Student student : students) {
          emitter.onNext(student);
          for (String cours : student.courses) {
            student.currentCourse = cours;
            Thread.sleep(1000);
            emitter.onNext(student);
          }
        }
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Student>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Student s) {
            Log.i(TAG, "onNext() :" + Thread.currentThread().getName());
            Log.i(TAG, "onNext() :" + s);
            tv.setText(s.name);
            tv1.setText(s.currentCourse);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
        });

  }
}
