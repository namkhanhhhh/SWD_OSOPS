package SWD392_OSOPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SWD392_OSOPS.entities.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByRoleName(String name);
  List<Role> findAll();
}
