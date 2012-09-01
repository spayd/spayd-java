/**
 *  Copyright (c) 2012, SmartPayment (www.SmartPayment.com).
 */
package com.smartpaymentformat.qr;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.smartpaymentformat.utilities.SmartPaymentConstants;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author petrdvorak
 */
public class SmartPaymentQRUtils {

    public static BufferedImage getQRCode(Integer size, String paymentString) throws IOException {
        if (size == null || size < SmartPaymentConstants.minQRSize) {
            size = SmartPaymentConstants.minQRSize;
        } else if (size > SmartPaymentConstants.maxQRSize) {
            size = SmartPaymentConstants.maxQRSize;
        }

        String data;
        // get a byte matrix for the data
        BitMatrix matrix = null;
        int h = size;
        int w = size;
        Writer writer = new MultiFormatWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "ASCII");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            matrix = writer.encode(paymentString, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
        } catch (com.google.zxing.WriterException e) {
            System.out.println(e.getMessage());
        }

        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        BufferedImage overlay = ImageIO.read(SmartPaymentQRUtils.class.getResource("paylibo.png"));

        double scale = ((double) image.getWidth() / (double) overlay.getWidth());

        // create the new image, canvas size is the max. of both image sizes

        BufferedImage combined = new BufferedImage(w, h + (int) (overlay.getHeight() * (1.0 + scale)), BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics2D g = (Graphics2D) combined.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(image, 0, 0, w, h, null);
        g.drawImage(overlay, (int) (w * 0.35), (int) (h), (int) (w * 0.30), (int) (0.30 * overlay.getHeight() * scale), null);

        return image;
    }
}
