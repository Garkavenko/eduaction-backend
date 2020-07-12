package ru.vsueducation.server.routes;

import ru.vsueducation.db.dao.UsersDao;
import ru.vsueducation.db.models.User;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.JWT;
import ru.vsueducation.server.utils.NetworkError;
import spark.Request;
import spark.Response;
import spark.Route;

public class AuthorizedRoute implements Route {
    private final RouteWithContext route;
    private final String forRole;

    public AuthorizedRoute(final RouteWithContext route, final String forRole) {
        this.route = route;
        this.forRole = forRole;
    }

    public AuthorizedRoute(final RouteWithContext route) {
        this.route = route;
        forRole = null;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final String token = request.headers("AUTH_TOKEN");
        final String error = NetworkError.toJson("Пользователь не авторизован", 401);
        final User user;
        if (token == null) return error;
        try {
            final Integer userId = JWT.getUserIdByToken(token);
            user = new UsersDao().getUserById(userId);
            if (forRole != null && !user.getRole().getAlias().equals(forRole)) {
                return NetworkError.toJson("Ошибка прав доступа", 403);
            }
        } catch (Exception ignored) {
            return error;
        }
        return route.handleWithContext(request, response, new Context(user));
    }
}
