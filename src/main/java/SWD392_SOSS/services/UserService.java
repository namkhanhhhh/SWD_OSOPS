package SWD392_SOSS.services;

import SWD392_SOSS.dtos.*;
import SWD392_SOSS.entities.User;
import SWD392_SOSS.exceptions.FileNotFoundException;
import SWD392_SOSS.exceptions.NoDataInListException;
import SWD392_SOSS.exceptions.OutOfPageException;
import SWD392_SOSS.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    User save(UserDto userDto);

    User save(UserAddDto userAddDto);

    User save(User user);

    User saveProfile(ProfileDto profileDto, String userName);

    User saveProfileCheckout(ProfileDto profileDto, String userName);

    User findByUsername(String username);

    int getUserId(String userName);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    public User getByResetPasswordToken(String token);

    public void updatePassword(User user, String newPassword);

    User findByEmail(String email);

    User findUserByOrderId(int orderId);

    PageDto getListUserFirstLoad(int page, int size, String search) throws NoDataInListException, OutOfPageException, FileNotFoundException;

    User saveUserRole(int userId, String roleName) throws UserNotFoundException;

    void saveUserActive(int userId, String status) throws UserNotFoundException;

    List<StatisticsUserOrder> TotalOderOfUser();

    List<StatisticsUserOrder> TotalOrderOfUserByDate(Date start,Date end);

    User findUserById(int userId);
}
