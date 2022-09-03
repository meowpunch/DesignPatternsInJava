package behavioral.observer;

interface Observer<T> {
  void handle(PropertyChangeEventArgs<T> args);
}
