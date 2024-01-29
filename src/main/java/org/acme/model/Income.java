package org.acme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents an income record.
 * <p></p>
 * The Income entity class is used to model the data for an income.
 * It includes details such as the income's unique identifier, the amount,
 * a description, and the date of the income.
 * <p></p>
 * {@code @Entity} Indicates that this class is a JPA entity.
 */
@Entity
public class Income {
  @Id
  private Long id;
  private BigDecimal amount;
  private String description;
  private Date date;


  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
