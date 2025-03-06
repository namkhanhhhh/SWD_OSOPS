package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.entities.UserDetail;
import SWD392_OSOPS.repositories.UserDetailRepository;
import SWD392_OSOPS.services.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired private UserDetailRepository userDetailRepository;
    @Override
    public UserDetail getUserDetailByUserId(int userId) {
        return userDetailRepository.getUserDetailByUserId(userId);
    }
}
