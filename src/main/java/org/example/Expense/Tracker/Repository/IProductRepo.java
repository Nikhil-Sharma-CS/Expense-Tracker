package org.example.Expense.Tracker.Repository;

import org.example.Expense.Tracker.Model.Product;
import org.example.Expense.Tracker.Model.User;
import org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByUser(User user);
}
