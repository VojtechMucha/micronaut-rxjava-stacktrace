package micronaut.rxjava.stacktrace;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Mono;

import java.time.Duration;
import javax.inject.Inject;

@Controller
public class GoogleCallingController {

    private final GoogleClient client;

    @Inject
    public GoogleCallingController(GoogleClient client) {
        this.client = client;
    }

    @Get
    public Mono<String> get() {
        // lets timeout whole Mono in 1s
        // client will timeout in 2s
        return client
                .get()
                .timeout(Duration.ofSeconds(1));
    }

}
