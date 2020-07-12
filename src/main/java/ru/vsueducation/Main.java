package ru.vsueducation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.vsueducation.server.core.Server;

public class Main {
    public static void main(final String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
        final Server server = new Server();
        server.start();
    }
}
