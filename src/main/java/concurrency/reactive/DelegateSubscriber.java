package concurrency.reactive;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

abstract class DelegateSubscriber<T, R> implements Subscriber<T> {

  private final Subscriber<?> subscriber;

  DelegateSubscriber(Subscriber<? super R> subscriber) {
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
