package behavioral.observer;

record PropertyChangeEventArgs<T> (T source, String propertyName, Object newValue) {}
