package SWD392_OSOPS.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SWD392_OSOPS.dtos.PageDto;
import SWD392_OSOPS.dtos.RequestSaveActiveDto;
import SWD392_OSOPS.dtos.RequestSaveUserRoleDto;
import SWD392_OSOPS.dtos.RequestSearchUserDto;
import SWD392_OSOPS.entities.User;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.exceptions.NoDataInListException;
import SWD392_OSOPS.exceptions.OutOfPageException;
import SWD392_OSOPS.exceptions.UserNotFoundException;
import SWD392_OSOPS.services.RoleService;
import SWD392_OSOPS.services.UserService;

@RestController
@AllArgsConstructor
public class AdminRestController {
    private UserService userService;

    private RoleService roleService;

    @PostMapping("/save-role/")
    public ResponseEntity saveRole(@RequestBody RequestSaveUserRoleDto requestSaveUserRoleDto) throws NoDataInListException, OutOfPageException, UserNotFoundException, FileNotFoundException {
        User user = userService.saveUserRole(requestSaveUserRoleDto.getUserId(), requestSaveUserRoleDto.getRoleName());
        PageDto pageDto = userService.getListUserFirstLoad(requestSaveUserRoleDto.getPage() - 1, 5, requestSaveUserRoleDto.getSearch());
        return ResponseEntity.ok(PageDto.builder().resultList(pageDto.getResultList()).
                roles(roleService.findAll()).
                currentPage(pageDto.getCurrentPage()).
                totalPage(pageDto.getTotalPage()).
                size(pageDto.getSize()).
                build());
    }

    @PostMapping("/save-active/")
    public ResponseEntity saveActive(@RequestBody RequestSaveActiveDto requestSaveActiveDto) throws UserNotFoundException, NoDataInListException, OutOfPageException, FileNotFoundException {
        userService.saveUserActive(requestSaveActiveDto.getUserId(), requestSaveActiveDto.getStatus());
        PageDto pageDto = userService.getListUserFirstLoad(requestSaveActiveDto.getPage() - 1, 5, requestSaveActiveDto.getSearch());
        return ResponseEntity.ok(PageDto.builder().resultList(pageDto.getResultList()).
                roles(roleService.findAll()).
                currentPage(pageDto.getCurrentPage()).
                totalPage(pageDto.getTotalPage()).
                size(pageDto.getSize()).
                build());
    }


}
