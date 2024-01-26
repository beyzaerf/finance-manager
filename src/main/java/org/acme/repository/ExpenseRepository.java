package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Expense;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ExpenseRepository implements PanacheRepository<Expense> {
    public List<Expense> findByDescription(String description) {
        return list("description", description);
    }

    public List<Expense> findByDate(Date date) {
        return list("date", date);
    }

    public List<Expense> sortByAmount(Sort.Direction direction) {
        return findAll(Sort.by("amount", direction)).list();
    }
}
