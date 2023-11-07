package br.com.fiap.epictask.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.user.User;
import br.com.fiap.epictask.user.UserService;

@Service
public class AddressService {

    @Autowired
    AddressRepository repository;

    @Autowired
    UserService userService;

    public List<Address> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void create(Address address) {
        repository.save(address);
    }

    public void catchAddress(Long id, User user) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("tarefa não encontrada");

        var task = optional.get();

        if (task.getUser() != null)
            throw new RuntimeException("tarefa já atribuída");

        task.setUser(user);
        repository.save(task);

    }

    public void dropAddress(Long id, User user) {
        var optional = repository.findById(id);
        if (optional.isEmpty())
            throw new RuntimeException("tarefa não encontrada");

        var task = optional.get();

        if (task.getUser() == null)
            throw new RuntimeException("tarefa não já atribuída");

        if (!task.getUser().equals(user))
            throw new RuntimeException("tarefa atribuída para outro usuário");

        task.setUser(null);
        repository.save(task);
    }

}
