package com.example.blogservice.entity.role;

public enum Permission {

    /**
     * ### User roles
     *
     * `[users.posts.rw](http://users.posts.rw)` - permission to publish posts
     *
     * ### Admin roles
     *
     * `[admin.posts.ro](http://admin.posts.ro)` - permission to see all posts, including `UNPUBLISHED` and `BLOCKED` posts
     *
     * `[admin.posts.rw](http://admin.posts.rw)` - permission to update, block/unblock any post
     *
     * `admin.admins.ro` - permission to see all admin users
     *
     * `admin.admins.rw` - permission to add new admin users or demote them back to regular users
     *
     * `admin.users.rw` - permission to modify user data, including password reset
     *
     * `admin.roles.ro` - permission to see admin roles
     *
     * `admin.roles.rw` - permission to modify any roles of any users, incl. revoking `users.posts.rw`
     */
    USERS_PUBLISH_POSTS("users.posts.rw"),
    ADMINS_READ_ALL_POSTS("admin.posts.ro"),
    ADMINS_UPDATE("admin.posts.rw"),
    ADMINS_SEE_ADMINS("admin.admins.ro"),
    ADMINS_MAKE_ADMINS("admin.admins.rw"),
    ADMINS_MODIFY_USERS("admin.users.rw"),
    ADMINS_READ_ADMIN_ROLES("admin.roles.ro"),
    ADMINS_MODIFY_ROLES("admin.roles.rw");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
