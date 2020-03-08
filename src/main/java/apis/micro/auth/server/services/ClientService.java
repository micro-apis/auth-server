package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.Client;
import apis.micro.auth.server.error.ErrorCodes;
import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import apis.micro.auth.server.models.ClientRequest;
import apis.micro.auth.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientService extends BaseService<Client> {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Mono<Client> createClient(ClientRequest clientRequest) {
        Client client = new Client()
                .setClientId(clientRequest.getClientId())
                .setClientSecret(passwordEncoder.encode(clientRequest.getClientSecret()))
                .setTokenValidity(LocalDateTime.now().plusDays(30))
                .setDeleted(false)
                .setEnabled(true);

        return clientRepository.save(client).onErrorResume(handleMongoDbMonoErrors);
    }
}
