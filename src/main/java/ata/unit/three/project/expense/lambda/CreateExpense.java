package ata.unit.three.project.expense.lambda;

import ata.unit.three.project.App;
import ata.unit.three.project.expense.lambda.models.Expense;
import ata.unit.three.project.expense.lambda.models.ExpenseList;
import ata.unit.three.project.expense.service.ExpenseService;
import ata.unit.three.project.expense.service.ExpenseServiceComponent;

import ata.unit.three.project.expense.service.exceptions.InvalidExpenseException;
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
public class CreateExpense implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));

        ExpenseService expenseService = App.expenseService();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        // Your Code Here
        try {

            Expense expense = gson.fromJson(input.getBody(), Expense.class);
            if(expense.getAmount() <= 0) {
                throw new InvalidExpenseException("Invalid amount");
            }
            String id = expenseService.createExpense(expense);

            return response
                    .withStatusCode(200)
                    .withBody(id);
        }catch(InvalidExpenseException e){
            return response
                    .withStatusCode(400);


        }


    }
}

