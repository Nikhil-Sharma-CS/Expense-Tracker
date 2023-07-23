package org.example.Expense.Tracker.Service;

import org.example.Expense.Tracker.Model.Product;
import org.example.Expense.Tracker.Model.User;
import org.example.Expense.Tracker.Repository.IProductRepo;
import org.example.Expense.Tracker.Repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    IProductRepo productRepo;

    @Autowired
    UserService userService;

    public String addProduct(Product product, String email) {

        User productUser = product.getUser();   //This gives us user but we need to extract its Id

        Integer userId = productUser.getUserId();   //This extracts our user ID

        User actualUser = userService.findById(userId); //This extracts actual user from our table

        if(actualUser != null)
        {
            if(actualUser.getUserEmail().equals(email))
            {
                product.setUser(actualUser);
                productRepo.save(product);
                return "Product Saved";
            }
            else
            {
                return "User Id and Email does not match";
            }
        }
        return "User not found with provided Id";
    }

    public Long getAmount(User user, LocalDate startDate, LocalDate endDate) {

        //Let's get a list of all products by user

        List<Product> products = productRepo.findByUser(user);

        Long amount = 0L;

        if(products == null)
            return amount;

        for(Product product : products)
        {
            LocalDate productDate = product.getProdDate();

            if((productDate.isAfter(startDate) || productDate.isEqual(startDate)) && (productDate.isBefore(endDate) || productDate.isEqual(endDate)))
            {
                amount += product.getProdPrice();
            }
        }
        return amount;
    }
}
