package br.com.kauamartins;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/calculadora-media")
public class MediaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            double nota1 = Double.parseDouble(
                request.getParameter("nota1")
            );

            double nota2 = Double.parseDouble(
                request.getParameter("nota2")
            );

            double nota3 = Double.parseDouble(
                request.getParameter("nota3")
            );

            String tipoMedia = request.getParameter("tipoMedia");

            if (!notaValida(nota1) ||
                !notaValida(nota2) ||
                !notaValida(nota3)) {

                responder(response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Notas inválidas",
                    "As notas devem estar entre 0 e 10.",
                    "",
                    ""
                );
                return;
            }

            double media;

            switch (tipoMedia) {
                case "aritmetica" ->
                    media = (nota1 + nota2 + nota3) / 3.0;

                case "ponderada" ->
                    media = (nota1 * 2 + nota2 * 3 + nota3 * 5)
                            / 10.0;

                default -> {
                    responder(response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Operação inválida",
                        "Selecione um tipo de média válido.",
                        "",
                        ""
                    );
                    return;
                }
            }

            String situacao;

            if (media >= 6.0) {
                situacao = "Aprovado";
            } else if (media >= 5.0) {
                situacao = "Exame";
            } else {
                situacao = "Reprovado";
            }

            String mensagem = String.format(
                Locale.US,
                "Média final: %.2f — Situação:",
                media
            );

            responder(response,
                HttpServletResponse.SC_OK,
                "Resultado do aluno",
                mensagem,
                situacao,
                classeDaSituacao(situacao)
            );

        } catch (NumberFormatException | NullPointerException e) {
            responder(response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Dados inválidos",
                "Informe as três notas corretamente.",
                "",
                ""
            );
        }
    }

    private boolean notaValida(double nota) {
        return Double.isFinite(nota)
                && nota >= 0.0
                && nota <= 10.0;
    }

    private String classeDaSituacao(String situacao) {
        return switch (situacao) {
            case "Aprovado" -> "aprovado";
            case "Reprovado" -> "reprovado";
            case "Exame" -> "exame";
            default -> "";
        };
    }

    private String escaparHtml(String texto) {
        return texto
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    private void responder(HttpServletResponse response, int status, String titulo,
                           String mensagem, String situacao, String classeSituacao)
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
        html = html.replace("{{situacao}}", escaparHtml(situacao));
        html = html.replace("{{classeSituacao}}", classeSituacao);
        html = html.replace("{{mensagem}}", escaparHtml(mensagem));

        response.getWriter().write(html);
    }
}