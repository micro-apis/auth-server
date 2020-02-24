package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.Client;
import apis.micro.auth.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Mono<Client> createClient() {
        Client client = new Client()
                .setClientId("api_1234")
                .setClientSecret(passwordEncoder.encode("secret_1234"))
                .setTokenValidity(LocalDateTime.now().plusDays(30))
                .setDeleted(false)
                .setEnabled(true);

        Mono<Client> clientMono = clientRepository.save(client);
        return clientMono;
    }
}
