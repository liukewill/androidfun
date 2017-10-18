package design.createpattern.builder;

/**
 * Created by kenan on 17/5/11.
 */
public class Pizza {
    private int size;
    private boolean cheese;
    private boolean bacon;

    public Pizza(Builder builder){
        this.size=builder.size;
        this.cheese=builder.cheese;
        this.bacon=builder.bacon;
    }

    public static class Builder{
        private int size;
        private boolean cheese;
        private boolean bacon;


        public Builder setSize(int size){
            this.size=size;
            return this;
        }

        public Builder setChese(boolean chese){
            this.cheese=cheese;
            return this;
        }

        public Builder setbacon(boolean bacon){
            this.bacon=bacon;
            return this;
        }

        public Pizza build(){
            Pizza pizza=new Pizza(this);
            return pizza;
        }
    }
}
