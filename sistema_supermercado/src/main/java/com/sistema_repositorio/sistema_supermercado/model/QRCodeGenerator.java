package com.sistema_repositorio.sistema_supermercado.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeGenerator {

    public static BufferedImage generateQRCodePix(String chavePix, double valor) throws WriterException, IOException {
        // Monta os dados do PIX
        String payload = "000201"
                + "26330014" // Identificador do Payload Format Indicator
                + "0014" // Tamanho do valor fixo (14 caracteres)
                + "br.gov.bcb.pix" // Valor fixo "br.gov.bcb.pix"
                + "01" // Indicador do método estático (01 para código QR dinâmico)
                + "25" // Identificador da chave PIX
                + String.format("%02d", chavePix.length()) + chavePix // Chave PIX
                + "5303" // Indicador do valor fixo (R$)
                + "986" // Moeda (BRL)
                + String.format("%02d", String.valueOf(valor).length()) + valor // Valor da transação
                + "5802BR" // Identificador do país (Brasil)
                + "5913" // Identificador do Merchant Account Information
                + "BR.GOV.BCB.BRCODE" // Valor fixo "BR.GOV.BCB.BRCODE"
                + "01" // Indicador do campo "GUI" (01 para chave aleatória)
                + "1" // Chave aleatória (utilizei 1 como exemplo)
                + "6008" // Indicador do campo "TxID" (identificador da transação)
                + "00000001" // Valor fixo "00000001"
                + "6304"; // CRC16

        // Gera o QR Code
        return generateQRCodeImage(payload, 300, 300);
    }

    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 0);
    
        Writer writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
    
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    

    private static String encodeToString(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, format, bos);
        byte[] imageBytes = bos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

}
