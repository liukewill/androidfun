package com.example.think.androidfun.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kenan on 18/3/21.
 */

public class Book implements Parcelable {

    public int bookId;
    public String bookName;

    public Book(int Id,String name){
        this.bookId=Id;
        this.bookName=name;
    }

    /**
     * 反序列化构造函数
     * 从序列化的对象中创建原始对象
     * @param in  序列化的对象
     */
    protected Book(Parcel in) {
        bookId=in.readInt();
        bookName=in.readString();
    }

    /**
     * 反序列化 Creator
     * 从序列化的对象中创建原始对象
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    /**
     * 返回当前对象内容描述
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化 写操作
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }
}
