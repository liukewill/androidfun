package design.structurepattern.responsibility.instance;

import design.structurepattern.responsibility.Interceptor;

public class Intercept02 implements Interceptor {

  @Override
  public boolean intercept(Chain chain) {
    try {
      System.out.println("Intercept02 - process");
      Thread.sleep(2000);
      chain.proceed(chain.getContext());
    } catch (InterruptedException e) {
      e.printStackTrace();
      return true;
    }

     return false;
  }
}
