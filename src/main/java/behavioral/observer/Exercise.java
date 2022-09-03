package behavioral.observer;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
    Game needs to propagate the change of the total number of rats to each rats
    Game is Observable/Publisher
 */
class Game {

  Set<Rat> rats = new HashSet<>(); // observers

  void addRat(Rat rat) {
    rats.add(rat);
    updateRatAttack();
  }

  void removeRat(Rat rat) {
    rats.remove(rat);
    updateRatAttack();
  }

  void updateRatAttack() {
    rats.forEach(
        rat -> rat.attack = rats.size()
    );
    System.out.println("update each rat's attack as " + rats.size());
  }
}

class Rat implements Closeable // subscription
{

  private final Game game;
  public int attack = 1; // keep the total number of rats

  public Rat(Game game) {
    this.game = game;
    game.addRat(this); // request
  }

  @Override
  public void close() {
    this.game.removeRat(this); //cancel
  }
}

public class Exercise {

  public static void main(String[] args) {

    var game = new Game();

    var r1 = new Rat(game);
    var r2 = new Rat(game);
    var r3 = new Rat(game);

    r1.close();
  }
}
