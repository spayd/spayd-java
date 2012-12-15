/**
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 */
package org.spayd.utilities;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.sun.media.sound.InvalidFormatException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author petrdvorak
 */
public class SpaydQRUtils {

    /**
     * Returns a branded QR code with a payment info of a given size.
     * @param size Size in pixels (without the branding)
     * @param paymentString A SPAYD string with payment information
     * @return An image with the payment QR code
     * @throws IOException 
     */
    public static BufferedImage getQRCode(Integer size, String paymentString) throws IOException {
        return getQRCode(size, paymentString, true);
    }

    /**
     * Generate a QR code with the payment information of a given size, optionally, with branding
     * @param size A size of the QR code (without the branding) in pixels
     * @param paymentString A SPAYD string with payment information
     * @param hasBranging A flag that can be used to turn on/off branding
     * @return An image with the payment QR code
     * @throws IOException 
     */
    public static BufferedImage getQRCode(Integer size, String paymentString, boolean hasBranging) throws IOException {
        if (size == null) {
            size = SpaydConstants.defQRSize;
        } else if (size < SpaydConstants.minQRSize) {
            size = SpaydConstants.minQRSize;
        } else if (size > SpaydConstants.maxQRSize) {
            size = SpaydConstants.maxQRSize;
        }

        BitMatrix matrix = null;
        int h = size;
        int w = size;
        int barsize = -1;
        Writer writer = new MultiFormatWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
            QRCode code = Encoder.encode(paymentString, ErrorCorrectionLevel.M, hints);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            barsize = size / (code.getMatrix().getWidth() + 8);
            matrix = writer.encode(paymentString, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
        } catch (com.google.zxing.WriterException e) {
            System.out.println(e.getMessage());
        }

        if (matrix == null || barsize < 0) {
            throw new InvalidFormatException();
        }

        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

        if (hasBranging) {
            Graphics2D g = (Graphics2D) image.getGraphics();

            BasicStroke bs = new BasicStroke(2);
            g.setStroke(bs);
            g.setColor(Color.BLACK);
            g.drawLine(0, 0, w, 0);
            g.drawLine(0, 0, 0, h);
            g.drawLine(w, 0, w, h);
            g.drawLine(0, h, w, h);

            String str = "QR Platba";
            int fontSize = size / 12;

            g.setFont(new Font("Verdana", Font.BOLD, fontSize));

            FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(str, g);

            g.setColor(Color.WHITE);
            g.fillRect(2 * barsize, h - fm.getAscent(), (int) rect.getWidth() + 4 * barsize, (int) rect.getHeight());

            int padding = 4 * barsize;

            BufferedImage paddedImage = new BufferedImage(w + 2 * padding, h + padding + (int) rect.getHeight(), image.getType());
            Graphics2D g2 = paddedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g2.setFont(new Font("Verdana", Font.BOLD, fontSize));
            g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, paddedImage.getWidth(), paddedImage.getHeight());
            g2.drawImage(image, padding, padding, Color.WHITE, null);

            g2.setColor(Color.BLACK);
            g2.drawString(str, padding + 4 * barsize, (int) (padding + h + rect.getHeight() - barsize));

            image = paddedImage;
        }

        return image;
    }
}
