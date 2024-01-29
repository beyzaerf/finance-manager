package org.acme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an expense record.
 * <p></p>
 * The Expense entity class is used to model the data for an expense.
 * It includes details such as the expense's unique identifier, the amount,
 * a description, and the date of the expense.
 * <p></p>
 * {@code @Entity} Indicates that this class is a JPA entity.
 */
@Entity
public class Expense {
  @Id
  @GeneratedValue
  private Long id;
  private BigDecimal amount;
  private String description;
  private LocalDate date;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
