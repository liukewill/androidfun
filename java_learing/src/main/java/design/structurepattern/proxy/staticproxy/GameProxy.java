package design.structurepattern.proxy.staticproxy;

/**
 * Created by kenan on 17/6/5.
 */
public class GameProxy implements IGame {

    private IGame player;

    public GameProxy(IGame player) {
        this.player = player;
    }

    public GameProxy(){
        try {
            player=new GamePlayer(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login() {
        player.login();
    }

    @Override
    public void upgrade() {
        player.upgrade();
    }


}
