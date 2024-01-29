package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Expense;
import org.acme.repository.ExpenseRepository;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@ApplicationScoped
public class ExpenseService {

  private static final Logger LOGGER = Logger.getLogger(ExpenseService.class.getName());

  @Inject
  ExpenseRepository expenseRepository;

  @Transactional
  public Expense addExpense(Expense expense) {
    LOGGER.debug("Adding a new expense...");
    try {
      expenseRepository.persist(expense);
    } catch (Exception e) {
      LOGGER.error("Error adding expense", e);
      throw e;
    }
    return expense;
  }

  @Transactional
  public Expense updateExpense(Long id, Expense expenseDetails) {
    Expense expense = getExpense(id);
    try {
      if (expense != null) {
        LOGGER.debug("Updating expense...");
        expense.setAmount(expenseDetails.getAmount());
        expense.setDate(expenseDetails.getDate());
        expense.setDescription(expenseDetails.getDescription());

        expenseRepository.persist(expense);
      }
    } catch (Exception e) {
      LOGGER.error("Error updating expense", e);
      throw e;
    }
    return expense;
  }

  @Transactional
  public void deleteExpense(Long id) {
    Expense expense = getExpense(id);
    try {
      if (expense != null) {
        LOGGER.debug("Deleting expense...");
        expenseRepository.delete(expense);
      }
    } catch (Exception e) {
      LOGGER.error("Error deleting expense", e);
      throw e;
    }
  }

  @Transactional
  public Expense getExpense(Long id) {
    try {
      return expenseRepository.findById(id);
    } catch (Exception e) {
      LOGGER.error("Error getting expense", e);
      throw e;
    }
  }

  @Transactional
  public List<Expense> getAllExpenses() {
    LOGGER.debug("Getting all expenses...");
    return expenseRepository.listAll();
  }

  @Transactional
  public BigDecimal calculateMonthlyExpenses(LocalDate month) {
    LOGGER.debug("Calculating expenses...");
    List<Expense> expenses = expenseRepository.list("date >= ?1 and date < ?2",
            month.withDayOfMonth(1),
            month.plusMonths(1).withDayOfMonth(1));
    return expenses.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
