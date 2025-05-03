package br.edu.unoesc.service;

import java.text.SimpleDateFormat;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.unoesc.model.Locacao;

import java.util.Base64;

@Service
public class QRCodeService {
	
    private String qrCodeApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String gerarQrCode(Locacao locacao) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataLocacao = sdf.format(locacao.getDataLocacao());
        String dataDevolucao = sdf.format(locacao.getDataDevolucao());

        String conteudoQrCode = String.format(
            "{\"cpf\":\"%s\", \"telefone\":\"%s\", \"dataLocacao\":\"%s\", \"dataDevolucao\":\"%s\"}",
            locacao.getCpf(), locacao.getTelefone(), dataLocacao, dataDevolucao
        );

        String url = qrCodeApiUrl + "?data=" + conteudoQrCode + "&size=200x200";

        ResponseEntity<byte[]> resposta = restTemplate.getForEntity(url, byte[].class);

        if (resposta.getStatusCode() == HttpStatus.OK && resposta.getBody() != null) {
            return Base64.getEncoder().encodeToString(resposta.getBody());
        } else {
            throw new RuntimeException("Erro ao gerar QR Code");
        }
    }
	
}
