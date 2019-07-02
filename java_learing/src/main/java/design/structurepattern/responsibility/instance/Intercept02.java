package design.structurepattern.responsibility.instance;

import java.util.Timer;

import design.structurepattern.responsibility.Interceptor;

public class Intercept01 implements Interceptor {

  @Override
  public boolean intercept(Chain chain) {
    try {
      System.out.println("Intercept01 - process");
      Thread.sleep(3000);
      chain.proceed(chain.getContext());
    } catch (InterruptedException e) {
      e.printStackTrace();
      return true;
    }

     return false;
  }
}
