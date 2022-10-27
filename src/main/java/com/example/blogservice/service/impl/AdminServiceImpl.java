package com.example.blogservice.service.impl;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.userDTOs.UserRolesResponseDTO;
import com.example.blogservice.dto.userDTOs.UserUpdateRequestDTO;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.repository.SessionRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    /**
     * Sets BlogAccount’s isAdmin=true
     * Removes roles users.posts.rw
     * Admin users are not allowed to create posts from
     * Only for admins with role admin.admins.rw
     * Should find all active user sessions and deprecate/remove them
     */
    public void promoteUser(Long userId) {
        var user = getUserById(userId);
        if (!user.getIsAdmin()) {
            user.setIsAdmin(true);
            user.getRoles().remove("users.posts.rw");
            userRepository.save(user);
            removeSessionsByUser(user);
        }
    }

    public void demoteUser(Long userId) {
        var user = getUserById(userId);
        if (user.getIsAdmin()) {
            user.setIsAdmin(false);
            user.getRoles().removeIf(role -> role.startsWith("admin."));
            user.getRoles().add("users.posts.rw");
            userRepository.save(user);
            removeSessionsByUser(user);
        }
    }

    public void updateUserFirstnameOrLastname(UserUpdateRequestDTO request, Long userId) {
        var user = getUserById(userId);
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        userRepository.save(user);
    }


    /**
     * TODO
     * Response:
     * [
     *  {
     *    userId
     *    name
     *    username
     *    idAdmin
     *    isROAdmin
     *    roles: []
     *  }
     * ]
     * Shows regular users to admin user.
     * <p>
     * For admins with role
     * admin.admins.ro - show all: admins and non-admins
     * <p>
     * If admin=true + role admin.admins.ro - show only admin users
     * <p>
     * If admin=true and no role admin.admins.ro - show empty array
     * ————————————-
     * <p>
     * roles[] - returns the result to admin users with role
     * admin.roles.ro
     * <p>
     * Otherwise - empty list or remove the key
     */
    public List<UserRolesResponseDTO> showUsersToAdmin(Boolean isAdmin) {
        return null;
    }

    public Set<String> showUserRolesToAdmin(KeyDTO keyDTO, Long userId) {
        var user = getUserById(userId);
        return user.getRoles();
    }

    public void grantUserNewRole(String newRole, Long userId, Boolean resetSession) {
        var user = getUserById(userId);
        user.getRoles().add(newRole);
        userRepository.save(user);

        if (resetSession) {
            removeSessionsByUser(user);
        }
    }

    public void deleteUserRole(String roleName, Long userId, Boolean resetSession) {
        var user = getUserById(userId);
        user.getRoles().remove(roleName);
        userRepository.save(user);

        if (resetSession) {
            removeSessionsByUser(user);
        }
    }

    public Set<String> showAvailableRoles(KeyDTO keyDTO) {
        if (keyDTO == null || keyDTO.getBlogUser() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return keyDTO.getBlogUser().getRoles();
    }

    private BlogUser getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void removeSessionsByUser(BlogUser user) {
        var sessions = sessionRepository.findAllByBlogUser(user);
        sessionRepository.deleteAll(sessions);
    }
}
