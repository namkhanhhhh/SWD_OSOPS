package SWD392_SOSS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_SOSS.entities.Role;
import SWD392_SOSS.repositories.RoleRepository;
import SWD392_SOSS.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired private RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
