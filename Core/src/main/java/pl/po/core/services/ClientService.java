package pl.po.core.services;

import pl.po.core.domain.Client;

public interface ClientService {

    Client get(Long id);

    Client add(Client client);

    Client update(Client client);

    void delete(Long id);
}
