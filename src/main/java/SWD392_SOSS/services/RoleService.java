package SWD392_SOSS.services;

import SWD392_SOSS.entities.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findAll();
}
