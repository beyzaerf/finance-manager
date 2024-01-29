package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.acme.model.Income;
import org.acme.repository.IncomeRepository;
import org.jboss.logging.Logger;

@ApplicationScoped
public class IncomeService {

  private static final Logger LOGGER = Logger.getLogger(IncomeService.class.getName());

  @Inject
  IncomeRepository incomeRepository;

  @Transactional
  public Income addIncome(Income income) {
    LOGGER.debug("Adding income...");
    try {
      incomeRepository.persist(income);
    } catch (Exception e) {
      LOGGER.error("Error adding income", e);
    }
    return income;
  }

  @Transactional
  public Income updateIncome(Long id, Income incomeDetails) {
    Income income = getIncome(id);
    try {
      LOGGER.debug("Updating income...");
      if (income != null) {
        income.setAmount(incomeDetails.getAmount());
        income.setDate(incomeDetails.getDate());
        income.setDescription(incomeDetails.getDescription());

        incomeRepository.persist(income);
      }
    } catch (Exception e) {
      LOGGER.error("Error updating income", e);
    }
    return income;
  }

  @Transactional
  public void deleteIncome(Long id) {
    Income income = getIncome(id);
    if (income != null) {
      incomeRepository.delete(income);
    } else {
      throw new RuntimeException("Income to be deleted doesnt exist.");
    }
  }

  @Transactional
  public Income getIncome(Long id) {
    return incomeRepository.findById(id);
  }

  @Transactional
  public List<Income> getAllIncomes() {
    return incomeRepository.listAll();
  }

  @Transactional
  public BigDecimal calculateMonthlyIncome(YearMonth month) {
    List<Income> incomes = incomeRepository.list("date >= ?1 and date < ?2",
            month.atDay(1),
            month.plusMonths(1).atDay(1));
    return incomes.stream()
            .map(Income::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
