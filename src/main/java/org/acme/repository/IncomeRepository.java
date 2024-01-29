package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;
import java.util.List;

import org.acme.model.Income;


/**
 * Repository class for the Income Entity.
 * Provides methods for querying Income records in the database.
 * Utilizes Quarkus' PanacheRepository to simplify database operations.
 *
 * <p></p> This repository includes methods for finding income records by description,
 * finding records by date and sorting records by amount.
 */
@ApplicationScoped
public class IncomeRepository implements PanacheRepository<Income> {
  public List<Income> findByDescription(String description) {
    return list("description", description);
  }

  public List<Income> findByDate(Date date) {
    return list("date", date);
  }

  public List<Income> sortByAmount(Sort.Direction direction) {
    return findAll(Sort.by("amount", direction)).list();
  }
}
