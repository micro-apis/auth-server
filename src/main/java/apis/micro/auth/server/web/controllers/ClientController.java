package apis.micro.auth.server.web.controllers;

import apis.micro.auth.server.documents.Client;
import apis.micro.auth.server.models.ClientRequest;
import apis.micro.auth.server.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    // TODO have a separate interceptor for checking isAdmin -> true
    @PostMapping("/api/public/client")
    public Mono<Client> createClient(@RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }
}
