package apis.micro.auth.server.controllers;

import apis.micro.auth.server.documents.Client;
import apis.micro.auth.server.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/api/client")
    public Mono<Client> createClient() {
        return clientService.createClient();
    }
}
