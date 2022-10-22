package concurrency.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallbackEx {

  interface SuccessCallBack<T> {
    void onSuccess(T t);
  }

  interface ExceptionCallBack {
    void onError(Throwable t);
  }

  static class CallbackFutureTask<T> extends FutureTask<T> {

    private final SuccessCallBack<T> sc;
    private final ExceptionCallBack ec;
    public CallbackFutureTask(Callable<T> callable, SuccessCallBack<T> onSuccess, ExceptionCallBack onError) {
      super(callable);
      this.sc = onSuccess;
      this.ec = onError;
    }

    @Override
    protected void done() {
      try {
        sc.onSuccess(get());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        ec.onError(e);
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    log.debug("Enter");
    final ExecutorService executorService = Executors.newCachedThreadPool();

    final long startTime = System.currentTimeMillis();

    // non-blocking and asynchronous and
    CallbackFutureTask<String> future = new CallbackFutureTask<>(
        () -> {
          log.debug("Processing task asynchronously");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

          return "AsyncResult";
        },
        s -> log.info("Result: " + s),
        e -> log.error("Error: " + e.getMessage())
    );

    executorService.execute(future);
    executorService.shutdown();

    log.debug("Is the async task done? " + future.isDone());
    log.debug("Processing the other task");
    Thread.sleep(1000);
    log.debug("Exit " + (System.currentTimeMillis() - startTime));
  }

}
