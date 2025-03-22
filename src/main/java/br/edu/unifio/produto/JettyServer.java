package br.edu.unifio.produto;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new ProdutoServlet()), "/produtos/*");

        server.setHandler(context);

        server.start();
        System.out.println("Servidor Jetty rodando em http://localhost:8080");
        server.join();
    }
}