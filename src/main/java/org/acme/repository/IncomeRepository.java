package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Income;

import java.util.Date;
import java.util.List;

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
