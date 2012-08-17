package com.edmunds.anonylead.rest.resource;

import com.edmunds.anonylead.DigestPeriod;
import com.edmunds.anonylead.Duration;
import com.edmunds.anonylead.Record;
import com.edmunds.anonylead.dao.AnonyLeadDao;
import com.edmunds.anonylead.exception.MaskedEmailNotFoundException;
import com.edmunds.anonylead.exception.MetaDataNotFoundException;
import com.edmunds.anonylead.exception.NoMaskedEmailHistoryException;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/17/12 Time: 1:10 AM
 */
@Component
@Path("anonyLead")
public class AnonyLeadResource {
    private final AnonyLeadDao anonyLeadDao;

    @Autowired
    public AnonyLeadResource(AnonyLeadDao anonyLeadDao) {
        this.anonyLeadDao = anonyLeadDao;
    }

    @POST
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean submitLead(@QueryParam("first") String firstName,
                              @QueryParam("last") String lastName,
                              @QueryParam("email") String email,
                              @QueryParam("duration") int duration,
                              @QueryParam("digest") int digestPeriod) {
        try {
            final Record record = new Record(email, firstName, lastName, Duration.values()[duration],
                DigestPeriod.values()[digestPeriod]);
            System.out.println(record.toString());
            return anonyLeadDao.putRecord(record);
        } catch(IOException e) {
            return false;
        }
    }

    @GET
    @Path("generate/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTempEmail(@PathParam("email") String email) {
        try {
            return anonyLeadDao.getTempEmail(email);
        } catch(IOException e) {
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.TEXT_PLAIN_TYPE).entity("Error retrieving data for " + email + "!").build());
        } catch(NoMaskedEmailHistoryException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN_TYPE).entity(e.getMessage()).build());
        }
    }

    @GET
    @Path("lookup/{temp}")
    @Produces(MediaType.APPLICATION_JSON)
    public Record getRecord(@PathParam("temp") String temp) {
        try {
            return anonyLeadDao.getRecord(temp);
        } catch(IOException e) {
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.TEXT_PLAIN_TYPE).entity("Error retrieving data for " + temp + "!").build());
        } catch(MaskedEmailNotFoundException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN_TYPE).entity(e.getMessage()).build());
        } catch(MetaDataNotFoundException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN_TYPE).entity(e.getMessage()).build());

        }
    }
}
