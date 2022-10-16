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
        // ^ DONE

        final TodoDAO todoDAO = new TodoDAO();
        Gson gson = new Gson();

        // Create/POST:
        post("/todos", (request, response) -> {
            response.type("application/json");

            Todo todo = gson.fromJson(request.body(), Todo.class);
            todo = todoDAO.create(todo);

            return gson.toJson(todo);
        });

        // Read/GET, one todo by id:
        get("/todos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");

            if (isNumber(id)) { // check if id is a number
                Todo todo = todoDAO.find(Long.valueOf(id));
                if (todo != null) {
                    return gson.toJson(todo);
                }
                return String.format("Todo with the id  \"%s\" not found!", id);
            }
            else {
                return String.format("The id \"%s\" is not a number!", id);
            }
        });

        // Read/GET, all todos:
        get("/todos", (request, response) -> {
            response.type("application/json");

            return gson.toJson(todoDAO.all());
        });

        // Update/PUT:
        put("/todos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");

            // check if id is a number
            if (isNumber(id)) {
                Todo editedTodo = gson.fromJson(request.body(), Todo.class);
                editedTodo = todoDAO.update(editedTodo, Long.valueOf(id));

                if (editedTodo != null) {
                    return gson.toJson(editedTodo);
                }
                else {
                    return gson.toJson("Todo not found or error in edit");
                }
            }
            else {
                return String.format("The id \"%s\" is not a number!", id);
            }
        });

        // Delete/DELETE:
        delete("/todos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if(isNumber(id)) {
                Todo todo = todoDAO.remove(Long.valueOf(id));
                if (todo != null) {
                    return gson.toJson("Success!");
                }
                else {
                    return String.format("Todo with the id  \"%s\" not found!", id);
                }
            }
            else {
                return String.format("The id \"%s\" is not a number!", id);
            }
        });
    }

    public static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
