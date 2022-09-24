package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;
import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        after((req, res) -> res.type("application/json"));

        // TODO: Implement API, such that the testcases succeed.

        TodoDAO todoDAO = new TodoDAO();
        Gson gson = new Gson();

        // Create/POST:
        post("/Todo", (request, response) -> {

            return null;
        });

        // Read/GET, one todo by id:
        get("/Todo/:id", (request, response) -> {
            response.type("application/json");

            Long id = Long.valueOf(request.params(":id"));
            Todo todo = todoDAO.find(Long.valueOf(id));
            if (todo != null) {
                return gson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS, new Gson().toJson(todo)));
            }
            return gson.toJson(new StandardResponse(
                    StatusResponse.ERROR, new Gson().toJson("Todo is not found!")));
        });
        // Read/GET, all todos:
        get("/Todo", (request, response) -> {
            response.type("application/json");

            return gson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJson(todoDAO.all())));
        });

        // Update/PUT:
        put("/Todo/:id", (request, response) -> {
            response.type("application/json");

            Long id = Long.valueOf(request.params(":id"));
            Todo todo = todoDAO.find(Long.valueOf(id));
            if (todo != null) {
                String summary = request.queryParams("summary");
                String description = request.queryParams("description");
                todoDAO.update(id, summary, description);
                return gson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS, new Gson().toJson(todo)));
            }
            else {
                return gson.toJson(new StandardResponse(
                        StatusResponse.ERROR, new Gson().toJson("Todo is not found or error in update")));
            }
        });

        // Delete/DELETE::

    }

}
