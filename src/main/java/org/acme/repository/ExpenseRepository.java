package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;
import java.util.List;

import org.acme.model.Expense;


/**
 * Repository class for the Expense entity.
 * Provides methods for querying Expense records in the database.
 * Utilizes Quarkus' PanacheRepository to simplify database operations.
 *
 * <p></p> This repository includes methods for finding expense records by description,
 * finding records by date, and sorting records by amount.
 */
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
