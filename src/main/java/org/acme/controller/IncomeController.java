package org.acme.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.acme.model.Income;
import org.acme.service.IncomeService;

/**
 * Provides RESTful web APIs to manage income records.
 * It offers CRUD operations and calculation of monthly total income.
 */
@Path("/income")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomeController {

  @Inject
  IncomeService incomeService;

  /**
   * Retrieves an income record by its ID.
   *
   * @param id The ID of the income record to be retrieved.
   * @return A response object containing the income record or a not-found status.
   */
  @GET
  @Path("/{id}")
  public Response getIncome(@PathParam("id") Long id) {
    Income income = incomeService.getIncome(id);
    if (income != null) {
      return Response.ok(income).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Creates a new income record.
   *
   * @param income The income object to be added.
   * @return A response object containing the income record.
   */
  @POST
  public Response addIncome(Income income) {
    Income createdIncome = incomeService.addIncome(income);
    return Response.ok(createdIncome).build();
  }

  /**
   * Updates an existing income record.
   *
   * @param id     The ID of the income record to be updated.
   * @param income The Income object to be updated.
   * @return A response object containing the updated income record or not found status.
   */
  @PUT
  @Path("/{id}")
  public Response updateIncome(@PathParam("id") Long id, Income income) {
    Income updatedIncome = incomeService.updateIncome(id, income);
    if (updatedIncome != null) {
      return Response.ok(updatedIncome).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Deletes an income record by ID.
   *
   * @param id The ID of the income object to be deleted.
   * @return A response object with no content status.
   */
  @DELETE
  @Path("/{id}")
  public Response deleteIncome(@PathParam("id") Long id) {
    incomeService.deleteIncome(id);
    return Response.noContent().build();
  }

  /**
   * Retrieves all income records.
   *
   * @return A response object containing a list of all income records or a not found status.
   */
  @GET
  public Response getAllIncome() {
    List<Income> incomes = incomeService.getAllIncomes();
    if (incomes != null) {
      return Response.ok(incomes).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Calculates the total income for a given year and month.
   *
   * @param yearMonthString The year and month in the format 'yyyy-MM-DD'.
   * @return A response object containing the calculated total or a bad request status in case of
   * an invalid format.
   */
  @GET
  @Path("monthly-total")
  public Response calculateMonthlyIncome(@QueryParam("yearMonth") String yearMonthString) {
    try {
      LocalDate date = LocalDate.parse(yearMonthString);
      BigDecimal monthlyTotal = incomeService.calculateMonthlyIncome(date);
      return Response.ok(monthlyTotal).build();
    } catch (DateTimeParseException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity("Invalid yearMonth format. Expected format: yyyy-MM").build();
    }
  }

}
