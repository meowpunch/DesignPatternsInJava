package concurrency.reactive;

import java.util.Iterator;

public class Iter {

  // Iterator Pattern
  public static void main(String[] args) {
    Iterable<Integer> iterOneToTen = () -> new Iterator<>() {
      int i = 0;
      final static int MAX = 10;

      @Override
      public boolean hasNext() {
        return i < MAX;
      }

      @Override
      public Integer next() {
        return ++i;
      }
    };

    for (int i : iterOneToTen) {
      System.out.println(i);
    }

    /*
      Same code with this

      for (var iter = iter0To10.iterator(); iter.hasNext(); ) {
        System.out.println(iter.next());
      }
    */
  }
}
