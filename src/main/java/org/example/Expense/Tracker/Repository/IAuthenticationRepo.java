package org.example.Expense.Tracker.Repository;

import org.example.Expense.Tracker.Model.AuthenticationToken;
import org.example.Expense.Tracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
