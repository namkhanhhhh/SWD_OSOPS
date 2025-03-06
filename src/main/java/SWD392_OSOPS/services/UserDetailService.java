package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.UserDetail;

public interface UserDetailService {
    UserDetail getUserDetailByUserId(int userId);
}
