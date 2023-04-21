package ata.unit.three.project.expense.lambda;

import ata.unit.three.project.App;
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

import java.util.HashMap;
import java.util.Map;

@ExcludeFromJacocoGeneratedReport
public class RetrieveExpensesByEmail
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ExpenseService expenseService = App.expenseService();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String email = input.getQueryStringParameters().get("email");
        // Your Code Here


        try {
            String expenses = gson.toJson(expenseService.getExpensesByEmail(email));
            return response
                    .withStatusCode(200)
                    .withBody(expenses);
        } catch (InvalidDataException e) {
            return response
                    .withStatusCode(400)
                    .withBody(e.getMessage());
        }


    }
    }

