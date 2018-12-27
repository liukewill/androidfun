package com.example.aes;

public class MyClass {

  // DexClassLoader类名进行加密后的结果字符串
  private static final String R_NAME = "1511444DD9EEF3E2C7205DCA0CC4D194525A533DEA7CE97F9886B9B2668AB223";
  // loadClass方法加密后的结果字符串
  private static final String M_NAME = "4D1E2BAC18AB1B49764F3392B5BF16B1";

  public static final String DNANE = "DexClassLoader";
  public static void main(String []args){
    final String clsName = AESUtil.decrypt(R_NAME, "");
    System.out.println(clsName);
  }
}
