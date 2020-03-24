package micronaut.rxjava.stacktrace;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

// Using port 81. It will ends with connection timeout. Timeout is set to 2s in application.yml
@Client("http://www.google.com:81/")
interface GoogleClient {

    @Get
    Mono<String> get();

}
