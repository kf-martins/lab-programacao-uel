package br.com.kauamartins;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CalculadoraServlet", urlPatterns = "/calcular")
public class CalculadoraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        final double operando1;
        final double operando2;

        try {
            operando1 = lerOperando(request.getParameter("operando1"));
            operando2 = lerOperando(request.getParameter("operando2"));
        } catch (NumberFormatException e) {
            responder(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Entrada inválida", "Informe dois números válidos.");
            return;
        }

        String operacao = request.getParameter("operacao");
        double resultado;

        if (operacao == null) {
            responder(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Operação inválida", "Selecione uma das quatro operações.");
            return;
        }

        switch (operacao) {
            case "somar" -> resultado = operando1 + operando2;
            case "subtrair" -> resultado = operando1 - operando2;
            case "multiplicar" -> resultado = operando1 * operando2;
            case "dividir" -> {
                if (operando2 == 0.0) {
                    responder(response, HttpServletResponse.SC_BAD_REQUEST,
                            "Não é possível dividir por zero", "Informe um segundo operando diferente de zero.");
                    return;
                }
                resultado = operando1 / operando2;
            }
            default -> {
                responder(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Operação inválida", "Selecione uma das quatro operações.");
                return;
            }
        }

        if (!Double.isFinite(resultado)) {
            responder(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Resultado inválido", "O resultado ultrapassou o limite de representação do tipo double.");
            return;
        }

        responder(response, HttpServletResponse.SC_OK,
                "Resultado", "Resultado: " + resultado);
    }

    private double lerOperando(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new NumberFormatException("Operando vazio");
        }

        double numero = Double.parseDouble(valor.trim().replace(',', '.'));
        if (!Double.isFinite(numero)) {
            throw new NumberFormatException("Número não finito");
        }
        return numero;
    }

    private String escaparHtml(String texto) {
    return texto
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;");
    }

    private void responder(HttpServletResponse response, int status, String titulo, String mensagem)
        throws IOException {

        response.setStatus(status);
        response.setContentType("text/html;charset=UTF-8");

        String classe = (status == HttpServletResponse.SC_OK) ? "sucesso" : "erro";

        String html;

        try (var input = getServletContext()
                .getResourceAsStream("/response.html")) {

            if (input == null) {
                response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Arquivo response.html não encontrado."
                );
                return;
            }

            html = new String(
                input.readAllBytes(),
                java.nio.charset.StandardCharsets.UTF_8
            );
        }

        html = html.replace("{{titulo}}", escaparHtml(titulo));
        html = html.replace("{{classe}}", classe);
        html = html.replace("{{mensagem}}", escaparHtml(mensagem));

        response.getWriter().write(html);
    }
}
