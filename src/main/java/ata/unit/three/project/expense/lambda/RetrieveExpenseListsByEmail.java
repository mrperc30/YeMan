package ata.unit.three.project.expense.lambda;
import ata.unit.three.project.App;
import ata.unit.three.project.expense.dynamodb.ExpenseItemList;
import ata.unit.three.project.expense.service.ExpenseService;
import ata.unit.three.project.expense.service.exceptions.InvalidDataException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExcludeFromJacocoGeneratedReport
public class RetrieveExpenseListsByEmail
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));


        ExpenseService expenseService = App.expenseService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String email = input.getQueryStringParameters().get("email");


        try {
            if(email == null) {
                return response
                        .withStatusCode(400)
                        .withBody("Invalid email address");
            }
            List<ExpenseItemList> expenseList = expenseService.getExpenseListByEmail(email);


            String output = gson.toJson(expenseService.getExpenseListByEmail(email));
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(output));
        }catch (InvalidDataException e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
