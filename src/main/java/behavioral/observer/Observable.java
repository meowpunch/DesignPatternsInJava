package behavioral.observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Observable<T> {

  private final List<Observer<T>> subscribedObservers = new ArrayList<>();

  public void subscribe(Observer<T> observer) {
    subscribedObservers.add(observer);
  }

  @SafeVarargs
  public final void subscribe(Observer<T>... observers) {
    subscribedObservers.addAll(Arrays.asList(observers));
  }

  protected void propertyChanged(T source, String propertyName, Object newValue) {
    subscribedObservers
        .forEach(
            observer -> observer.handle(new PropertyChangeEventArgs<>(source, propertyName, newValue))
        );
  }
}
