package concurrency.reactive;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

abstract class DelegateSubscriber<T> implements Subscriber<T> {

  private final Subscriber<T> subscriber;

  DelegateSubscriber(Subscriber<T> subscriber) {
    this.subscriber = subscriber;
  }

  @Override
  public void onSubscribe(Subscription s) {
    subscriber.onSubscribe(s);
  }

  @Override
  public void onError(Throwable t) {
    subscriber.onError(t);
  }

  @Override
  public void onComplete() {
    subscriber.onComplete();
  }
}
