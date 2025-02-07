package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.dtos.*;
import SWD392_OSOPS.entities.Cart;
import SWD392_OSOPS.entities.Role;
import SWD392_OSOPS.entities.User;
import SWD392_OSOPS.entities.UserDetail;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.exceptions.NoDataInListException;
import SWD392_OSOPS.exceptions.OutOfPageException;
import SWD392_OSOPS.exceptions.UserNotFoundException;
import SWD392_OSOPS.repositories.RoleRepository;
import SWD392_OSOPS.repositories.UserRepository;
import SWD392_OSOPS.services.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public int getUserId(String userName) {
        return userRepository.findByUsername(userName).getUserId();
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
        user.setResetPasswordToken(token);
        userRepository.save(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByOrderId(int orderId) {
        return userRepository.getUserByOrderId(orderId);
    }

    @Override
    public PageDto getListUserFirstLoad(int page, int size, String search) throws NoDataInListException, OutOfPageException, FileNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.isNull(search)) {
            search = "";
        }
        Page<User> userRequest = userRepository.findAllUser(search, pageable);
        if (page > userRequest.getTotalPages() - 1) {
            throw new FileNotFoundException("Page not found");
        }
        return PageDto.builder().resultList(userRequest.getContent()).currentPage(userRequest.getNumber() + 1).totalPage(userRequest.getTotalPages()).size(2).build();
    }

    @Override
    public User saveUserRole(int userId, String roleName) throws UserNotFoundException {
        Role role = roleRepository.findByRoleName(roleName);
        User user = new User();
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Not found user"));
        user = userRepository.findById(userId).get();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void saveUserActive(int userId, String status) throws UserNotFoundException {
        User user = new User();
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Not found user"));
        user = userRepository.findById(userId).get();
        if (status.equalsIgnoreCase("ACTIVE")) {
            user.setStatus("INACTIVE");
        } else {
            user.setStatus("ACTIVE");
        }
        userRepository.save(user);
    }

    @Override
    public User findUserById(int userId) {
        if (userRepository.existsById(userId)) {
            return userRepository.findById(userId).get();
        }
        return null;
    }

    @Override
    public List<StatisticsUserOrder> TotalOderOfUser() {
        if (userRepository.ListTotalOrderOfUser() == null) return null;
        List<StatisticsUserOrder> list = userRepository.ListTotalOrderOfUser();
        int size = Math.min(10, list.size());

        return list.subList(0, size);
    }

    @Override
    public List<StatisticsUserOrder> TotalOrderOfUserByDate(Date start, Date end) {
        LocalDate startDate = convertToLocalDate(start);
        LocalDate endDate = convertToLocalDate(end);

        List<StatisticsUserOrder> results = userRepository.ListTotalOrderOfUserByDate(startDate, endDate);
        int size = Math.min(10, results.size());
        return results != null ? results.subList(0, size) : null;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        Role role = roleRepository.findByRoleName("USER");
        role.setUsers(List.of(user));
        user.setRoles(List.of(role));
        Cart cart = new Cart();
        UserDetail userDetail = new UserDetail();
        user.setCart(cart);
        user.setUserDetail(userDetail);
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    @Override
    public User save(UserAddDto userAddDto) {
        User user = new User();
        user.setEmail(userAddDto.getEmail());
        user.setUsername(userAddDto.getUsername());
        user.setPassword(userAddDto.getPassword());
        Role role = roleRepository.findByRoleName(userAddDto.getRole());
        role.setUsers(List.of(user));
        user.setRoles(List.of(role));
        UserDetail userDetail = new UserDetail();
        user.setUserDetail(userDetail);
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveProfile(ProfileDto profileDto, String userName) {
        User user = userRepository.findByUsername(userName);
        user.getUserDetail().setFirstName(profileDto.getFirstName());
        user.getUserDetail().setLastName(profileDto.getLastName());
        user.getUserDetail().setGender(profileDto.getGender());
        user.getUserDetail().setPhoneNumber(profileDto.getPhoneNumber());
        user.getUserDetail().setAddress(profileDto.getAddress());
        return userRepository.save(user);
    }

    @Override
    public User saveProfileCheckout(ProfileDto profileDto, String userName) {
        User user = userRepository.findByUsername(userName);
        user.getUserDetail().setFirstName(profileDto.getFirstName());
        user.getUserDetail().setLastName(profileDto.getLastName());
        user.getUserDetail().setPhoneNumber(profileDto.getPhoneNumber());
        user.getUserDetail().setAddress(profileDto.getAddress());
        return userRepository.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
