package org.example.projectassignment.model.user;

import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.user.informationuser.User;

public class ManagerUser {
    private User user;
    private ManagerCategory managerCategory;

    public ManagerUser(User user, ManagerCategory managerCategory) {
        this.user = user;
        this.managerCategory = managerCategory;
    }

    public ManagerCategory getManagerCategory() {
        return managerCategory;
    }

    public void setManagerCategory(ManagerCategory managerCategory) {
        this.managerCategory = managerCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
