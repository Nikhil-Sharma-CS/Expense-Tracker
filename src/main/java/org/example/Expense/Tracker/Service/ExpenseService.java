package org.example.Expense.Tracker.Service;

import org.example.Expense.Tracker.Model.Expense;
import org.example.Expense.Tracker.Model.User;
import org.example.Expense.Tracker.Repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    IExpenseRepo expenseRepo;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public String addExpense(String email, LocalDate startDate, LocalDate endDate) {
        User user = userService.getFirstByEmail(email);

        Expense expense = new Expense();

        expenseRepo.save(expense);

        expense.setUser(user);

        expense.setExpEndDate(endDate);

        expense.setExpStartDate(startDate);

        Long amount = productService.getAmount(user, startDate, endDate); //This will get us the amount user spend for given date range

        expense.setExpAmount(amount);

        expenseRepo.save(expense);

        return "Expense Saved";
    }

    public List<Expense> getAllExpense(String email) {
        User user = userService.getFirstByEmail(email);

        return expenseRepo.findByUser(user);
    }

    public String deleteExpense(String email, Integer expenseId) {
        User user = userService.getFirstByEmail(email);

        if(expenseRepo.existsById(expenseId))
        {
            if(expenseRepo.findFirstByUser(user) != null)
            {
                expenseRepo.deleteById(expenseId);
                return "Expense Deleted";
            }
            return "Expense with user not found";
        }
        return "Expense Not Found";
    }
}
