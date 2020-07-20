package ru.vsueducation.server.routes;
import com.google.gson.JsonObject;
import ru.vsueducation.db.dao.UsersDao;
import ru.vsueducation.db.models.User;
import ru.vsueducation.server.utils.JWT;
import ru.vsueducation.server.utils.NetworkError;
import ru.vsueducation.utils.PasswordUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class Authorization implements Route {
    @Override
    public Object handle(Request request, Response response) {
        final String login = request.queryParams("login");
        final String password = request.queryParams("password");
        response.type("application/json");
        User user = null;
        try {
            user = new UsersDao().getUserByLogin(login);
        } catch (Exception ignored) {}
        if (user == null || !PasswordUtils.verifyUserPassword(password, user.getPassword(), user.getSalt())) {
            response.status(401);
            return NetworkError.toJson("Неправильный логин или пароль", 401);
        }
        response.status(200);
        final String jwt = JWT.createJWT(user.getId());
        response.header("Set-Cookie", "AUTH_TOKEN=" + jwt + "; Path=/");
        final JsonObject json = new JsonObject();
        json.addProperty("AUTH_TOKEN", jwt);
        return json.toString();
    }
}
