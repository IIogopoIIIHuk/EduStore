package EduStore_Pet.Pet_pr.service;

import EduStore_Pet.Pet_pr.entity.Role;
import EduStore_Pet.Pet_pr.repo.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NoSuchElementException("Role not found in database"));
    }

}
