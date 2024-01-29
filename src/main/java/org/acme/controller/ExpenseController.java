package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.acme.model.Expense;
import org.acme.service.ExpenseService;

/**
 * Provides RESTful web APIs to manage expense records.
 * It offers CRUD operations and calculation of monthly total expenses.
 */
@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseController {

  @Inject
  ExpenseService expenseService;

  /**
   * Retrieves an expense record by its ID.
   *
   * @param id The ID of the expense record to be retrieved.
   * @return A response object containing the expense record or a not-found status.
   */
  @GET
  @Path("/{id}")
  public Response getExpense(@PathParam("id") Long id) {
    Expense expense = expenseService.getExpense(id);
    if (expense != null) {
      return Response.ok(expense).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Retrieves all expense records.
   *
   * @return A response object containing a list of all expense records or a not found status.
   */
  @GET
  public Response getAllExpenses() {
    List<Expense> expenses = expenseService.getAllExpenses();
    if (expenses != null) {
      return Response.ok(expenses).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Creates a new income record.
   *
   * @param expense The expense object to be added.
   * @return A response object containing the expense record.
   */
  @POST
  public Response addExpense(Expense expense) {
    Expense createdExpense = expenseService.addExpense(expense);
    return Response.ok(createdExpense).build();
  }

  /**
   * Updates an existing income record.
   *
   * @param id      The ID of the expense record to be updated.
   * @param expense The expense object to be updated.
   * @return A response object containing the updated expense record or not found status.
   */
  @PUT
  @Path("/{id}")
  public Response updateExpense(@PathParam("id") Long id, Expense expense) {
    Expense updatedExpense = expenseService.updateExpense(id, expense);
    if (updatedExpense != null) {
      return Response.ok(updatedExpense).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * Deletes an expense record by ID.
   *
   * @param id The ID of the expense object to be deleted.
   * @return A response object with no content status.
   */
  @DELETE
  @Path("/{id}")
  public Response deleteExpense(@PathParam("id") Long id) {
    expenseService.deleteExpense(id);
    return Response.noContent().build();
  }

  /**
   * Calculates the total expenses for a given year and month.
   *
   * @param yearMonthString The year and month in the format 'yyyy-MM-DD'.
   * @return A response object containing the calculated total or a bad request status in case of
   * an invalid format.
   */
  @GET
  @Path("monthly-total")
  public Response calculateMonthlyExpenses(@QueryParam("yearMonth") String yearMonthString) {
    try {
      YearMonth yearMonth = YearMonth.parse(yearMonthString);
      BigDecimal monthlyTotal = expenseService.calculateMonthlyExpenses(yearMonth);
      return Response.ok(monthlyTotal).build();
    } catch (DateTimeParseException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity("Invalid yearMonth format. Expected format: yyyy-MM").build();
    }
  }
}
