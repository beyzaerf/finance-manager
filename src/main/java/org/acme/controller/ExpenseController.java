package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Expense;
import org.acme.service.ExpenseService;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseController {

    @Inject
    ExpenseService expenseService;

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

    @GET
    public Response getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if (expenses != null) {
            return Response.ok(expenses).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addExpense(Expense expense) {
        Expense createdExpense = expenseService.addExpense(expense);
        return Response.ok(createdExpense).build();
    }

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

    @DELETE
    @Path("/{id}")
    public Response deleteExpense(@PathParam("id") Long id) {
        expenseService.deleteExpense(id);
        return Response.noContent().build();
    }

    @GET
    @Path("monthly-total")
    public Response calculateMonthlyExpenses(@QueryParam("yearMonth") String yearMonthString) {
        try {
            YearMonth yearMonth = YearMonth.parse(yearMonthString);
            BigDecimal monthlyTotal = expenseService.calculateMonthlyExpenses(yearMonth);
            return Response.ok(monthlyTotal).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid yearMonth format. Expected format: yyyy-MM").build();
        }
    }
}
