package org.example.Expense.Tracker.Controller;

import jakarta.validation.Valid;
import org.example.Expense.Tracker.Model.DTO.SignInInput;
import org.example.Expense.Tracker.Model.DTO.SignUpOutput;
import org.example.Expense.Tracker.Model.Expense;
import org.example.Expense.Tracker.Model.Product;
import org.example.Expense.Tracker.Model.User;
import org.example.Expense.Tracker.Service.AuthenticationService;
import org.example.Expense.Tracker.Service.ExpenseService;
import org.example.Expense.Tracker.Service.ProductService;
import org.example.Expense.Tracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    AuthenticationService authenticationService;

    //Let's Make User Sign In Sign Out Sign Up

    @PostMapping("User/SignUp")
    public SignUpOutput UserSignUp(@RequestBody User user)
    {
        return userService.UserSignUp(user);
    }

    @PostMapping("User/SignIn")
    public String UserSignIn(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.UserSignIn(signInInput);
    }

    @DeleteMapping("User/SignOut")
    public String UserSignOut(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.UserSignOut(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

    //Now user will enter details with product details to add to his expense report
    @PostMapping("product")
    public String addProduct(@RequestBody Product product, String email, String token)
    {
        if(authenticationService.authenticate(email,token))
        {
            return productService.addProduct(product, email);
        }
        else
            return "Authentication Failed";
    }

    //Now user can create their expense
    @PostMapping("expense")
    public String addExpense(LocalDate startDate, LocalDate endDate, String email, String token)
    {
        if(authenticationService.authenticate(email,token))
        {
            return expenseService.addExpense(email, startDate, endDate);
        }
        return "Authentication Failed";
    }

    //See all expenses
    @GetMapping("expense")
    public List<Expense> getAllExpense(String email, String token)
    {
        if(authenticationService.authenticate(email,token))
        {
            return expenseService.getAllExpense(email);
        }
        return null;
    }

    //Now lets delete our expense
    @DeleteMapping("expense")
    public String deleteExpense(String email, String token, Integer ExpenseId)
    {
        if(authenticationService.authenticate(email, token))
        {
            return expenseService.deleteExpense(email, ExpenseId);
        }
        return "Authentication Failed";
    }
}
