package ata.unit.three.project.expense.lambda;

import ata.unit.three.project.App;
import ata.unit.three.project.expense.lambda.models.Expense;
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
public class UpdateExpense implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        ExpenseService expenseService = App.expenseService();
        String expenseId = input.getPathParameters().get("expenseId");
        Expense expense = gson.fromJson(input.getBody(), Expense.class);
        // Your Code Here
        try {
            if(expenseService.getExpenseById(expenseId) == null){
                return response
                        .withStatusCode(404);
            }
            expenseService.updateExpense(expenseId, expense);
            return response
                    .withStatusCode(204);
        }catch (ItemNotFoundException e) {
            return response
                    .withStatusCode(404)
                    .withBody(e.getMessage());


        }


    }
}
