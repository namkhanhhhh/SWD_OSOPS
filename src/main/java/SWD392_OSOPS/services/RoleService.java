package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findAll();
}
