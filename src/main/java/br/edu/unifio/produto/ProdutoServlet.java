package br.edu.unifio.produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/produtos/*")
public class ProdutoServlet extends HttpServlet {
    private static List<Produto> produtos = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        produtos.add(new Produto(1, "Produto 1", 10.0, 100));
        produtos.add(new Produto(2, "Produto 2", 20.0, 200));
        produtos.add(new Produto(3, "Produto 3", 30.0, 300));
        produtos.add(new Produto(4, "Produto 4", 40.0, 400));
        produtos.add(new Produto(5, "Produto 5", 50.0, 500));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Produto produto = objectMapper.readValue(req.getInputStream(), Produto.class);
        produtos.add(produto);

        Resposta resposta = new Resposta();
        resposta.setStatus(201);
        resposta.setMensagem("Produto incluído com sucesso");

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(resposta));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(produtos));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Produto produtoAtualizado = objectMapper.readValue(req.getInputStream(), Produto.class);

        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(produtoAtualizado.getCodigo())) {
                produto.setNome(produtoAtualizado.getNome());
                produto.setPreco(produtoAtualizado.getPreco());
                produto.setQuantidade(produtoAtualizado.getQuantidade());
                break;
            }
        }

        Resposta resposta = new Resposta();
        resposta.setStatus(200);
        resposta.setMensagem("Produto editado com sucesso");

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(resposta));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Produto produtoRemovido = objectMapper.readValue(req.getInputStream(), Produto.class);

        produtos.removeIf(produto -> produto.getCodigo().equals(produtoRemovido.getCodigo()));

        Resposta resposta = new Resposta();
        resposta.setStatus(200);
        resposta.setMensagem("Produto removido com sucesso");

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(resposta));
    }
}