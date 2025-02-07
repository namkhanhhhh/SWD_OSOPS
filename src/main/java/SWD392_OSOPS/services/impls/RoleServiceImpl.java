package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.entities.Role;
import SWD392_OSOPS.repositories.RoleRepository;
import SWD392_OSOPS.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired private RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
