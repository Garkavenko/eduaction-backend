package ru.vsueducation;

import ru.vsueducation.server.core.JettyServer;

public class Main {
    public static void main(final String[] args) throws Exception {
        final JettyServer server = new JettyServer();
        server.start();
    }
}
