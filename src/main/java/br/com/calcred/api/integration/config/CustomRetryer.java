package br.com.calcred.api.integration.config;

import br.com.calcred.api.integration.exception.IntegrationException;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.http.HttpStatus;
import java.util.concurrent.TimeUnit;

public class CustomRetryer implements Retryer {

    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt;
    long sleptForMillis;

    public CustomRetryer() {
        this(100L, TimeUnit.SECONDS.toMillis(1L), 5);
    }

    public CustomRetryer(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    protected long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public void continueOrPropagate(RetryableException e) {
        if (this.attempt++ >= this.maxAttempts) {
            if (e.status() == -1)
                throw new IntegrationException( HttpStatus.INTERNAL_SERVER_ERROR , e );

            throw new IntegrationException(HttpStatus.valueOf(e.status()), e );
        } else {
            long interval;
            if (e.retryAfter() != null) {
                interval = e.retryAfter().getTime() - this.currentTimeMillis();
                if (interval > this.maxPeriod) {
                    interval = this.maxPeriod;
                }

                if (interval < 0L) {
                    return;
                }
            } else {
                interval = this.nextMaxInterval();
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException var5) {
                Thread.currentThread().interrupt();
                throw e;
            }

            this.sleptForMillis += interval;
        }
    }

    long nextMaxInterval() {
        long interval = (long)((double)this.period * Math.pow(1.5D, (double)(this.attempt - 1)));
        return interval > this.maxPeriod ? this.maxPeriod : interval;
    }

    public Retryer clone() {
        return new CustomRetryer(this.period, this.maxPeriod, this.maxAttempts);
    }
}
