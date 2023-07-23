package org.example.Expense.Tracker.Repository;

import org.example.Expense.Tracker.Model.Expense;
import org.example.Expense.Tracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExpenseRepo extends JpaRepository<Expense, Integer> {
    List<Expense> findByUser(User user);

    Expense findFirstByUser(User user);
}
