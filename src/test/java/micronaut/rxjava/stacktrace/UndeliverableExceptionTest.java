package micronaut.rxjava.stacktrace;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

@MicronautTest
class UndeliverableExceptionTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @org.junit.jupiter.api.Test
    void test() throws InterruptedException {
        try {
            client
                    .retrieve("/")
                    .blockingFirst();
            Assertions.fail();
        } catch (HttpClientResponseException e) {
            // this is OK, GoogleCallingController timeouts in 1s
            Assertions.assertEquals(
                    "Internal Server Error: Did not observe any item or terminal signal within 1000ms in 'from' (and no fallback has been configured)",
                    e.getMessage());
        }
        // wait for GoogleClient connection timeout
        Thread.sleep(2000);
        // this failure is not important
        // important is that UndeliverableException is printed into stderr
        Assertions.fail("Check console for UndeliverableException");
    }

}
