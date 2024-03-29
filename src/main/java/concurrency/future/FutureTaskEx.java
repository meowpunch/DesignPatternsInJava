package concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureTaskEx {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    log.debug("Enter");
    final ExecutorService executorService = Executors.newCachedThreadPool();

    final long startTime = System.currentTimeMillis();

    // non-blocking and asynchronous
    // similar to callback style
    FutureTask<String> future = new FutureTask<>(
        () -> {
          log.debug("Processing task asynchronously");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

          return "AsyncResult";
        }
    ) {
      @Override
      protected void done() {
        try {
          log.info("Result: " + get());
        } catch (InterruptedException | ExecutionException e) {
          log.error("Error: " + e.getMessage());
        }
      }
    };

    executorService.execute(future);
    executorService.shutdown();

    log.debug("Is the async task done? " + future.isDone());
    log.debug("Processing the other task");
    Thread.sleep(1000);
    log.debug("Exit " + (System.currentTimeMillis() - startTime));
  }
}
