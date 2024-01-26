package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Income;
import org.acme.service.IncomeService;

@Path("/income")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomeController {

    @Inject
    IncomeService incomeService;

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

    @POST
    public Response addIncome(Income income) {
        Income createdIncome = incomeService.addIncome(income);
        return Response.ok(createdIncome).build();
    }

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

    @DELETE
    @Path("/{id}")
    public Response deleteIncome(@PathParam("id") Long id) {
        incomeService.deleteIncome(id);
        return Response.noContent().build();
    }

}
