package com.sistema_repositorio.sistema_supermercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.ResponseOk;
import com.sistema_repositorio.sistema_supermercado.model.Pagamento;
import com.sistema_repositorio.sistema_supermercado.model.QRCodeGenerator;
import com.sistema_repositorio.sistema_supermercado.repository.pagamentoRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;

import com.google.zxing.WriterException;

import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import jakarta.validation.Valid;

import java.util.Optional;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/pagamentos")
@Validated
public class PagamentoController {

    @Autowired
    private pagamentoRepository pagamentoRepository;

    public PagamentoController(pagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @GetMapping
    public ResponseEntity<Object> listarPagamentos() {
        long count = pagamentoRepository.count();
        if (count == 0) {
            ErroResponse erroResponse = new ErroResponse("Não há pagamentos cadastrados");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(this.pagamentoRepository.findAll());
        }
    }

    @PostMapping
public ResponseEntity<Object> adicionarPagamento(@Valid @RequestBody Pagamento pagamento) {
    pagamento.setId(sequenceGenerator.generateSequence(Pagamento.SEQUENCE_NAME));
    if (pagamento.getMetodoPagamento().equalsIgnoreCase("pix")) {
        String chave = "70783208405";
        try {
            BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(chave, 300, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] qrCodeBytes = baos.toByteArray();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeBytes);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(this.pagamentoRepository.save(pagamento));
}

    // Método para converter BufferedImage em array de bytes
    private byte[] bufferedImageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPagamento(@PathVariable long id) {
        Optional<Pagamento> pagamento = this.pagamentoRepository.findById(id);
        if (pagamento.isPresent()) {
            this.pagamentoRepository.delete(pagamento.get());
            ResponseOk responseOk = new ResponseOk("Pagamento excluído com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(responseOk);
        } else {
            ErroResponse erroResponse = new ErroResponse("Pagamento não encontrado na base de dados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPagamentoPorId(@PathVariable long id) {
        Optional<Pagamento> pagamento = this.pagamentoRepository.findById(id);
        if (pagamento.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(pagamento);
        } else {
            ErroResponse erroResponse = new ErroResponse("Pagamento não encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }
    }

    private static BufferedImage generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}