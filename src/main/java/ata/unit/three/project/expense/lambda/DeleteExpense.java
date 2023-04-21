package ata.unit.three.project.expense.lambda;

import ata.unit.three.project.App;

import ata.unit.three.project.expense.dynamodb.ExpenseItem;
import ata.unit.three.project.expense.service.ExpenseService;
import ata.unit.three.project.expense.service.exceptions.InvalidDataException;

import ata.unit.three.project.expense.service.exceptions.ItemNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ExcludeFromJacocoGeneratedReport
public class DeleteExpense implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));
        ExpenseService expenseService = App.expenseService();
        String expenseId = input.getPathParameters().get("expenseId");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {

            expenseService.deleteExpense(expenseId);
            response.setStatusCode(204);

        }  catch (InvalidDataException e) {
            log.error(e);
        response.setStatusCode(400);
    }
        return response;

    }
}
