/**
 * FreeDesktopSearch - A Search Engine for your Desktop
 * Copyright (C) 2013 Mirko Sertic
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.mirkosertic.desktopsearch.pdfpreview;

import de.mirkosertic.desktopsearch.Preview;
import de.mirkosertic.desktopsearch.PreviewConstants;
import de.mirkosertic.desktopsearch.PreviewGenerator;
import de.mirkosertic.desktopsearch.SupportedDocumentType;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class PDFPreviewGenerator implements PreviewGenerator, PreviewConstants {

    private static final Logger LOGGER = Logger.getLogger(PDFPreviewGenerator.class);

    private final Set<SupportedDocumentType> suppportedDocumentTypes;

    public PDFPreviewGenerator() {
        suppportedDocumentTypes = new HashSet<>();
        suppportedDocumentTypes.add(SupportedDocumentType.pdf);
    }

    @Override
    public Preview createPreviewFor(File aFile) {
        try(PDDocument theDocument = PDDocument.load(aFile))  {
            PDPageTree thePages = theDocument.getPages();
            if (thePages.getCount() == 0) {
                return null;
            }
            PDPage theFirstPage = (PDPage) thePages.get(0);

            PDRectangle mBox = theFirstPage.getMediaBox();
            float theWidthPt = mBox.getWidth();
            int theWidthPx = THUMB_WIDTH; // Math.round(widthPt * scaling);
            int theHeightPx = THUMB_HEIGHT; // Math.round(heightPt * scaling);
            float theScaling = THUMB_WIDTH / theWidthPt; // resolution / 72.0F;

            BufferedImage theImage = new BufferedImage(theWidthPx, theHeightPx, BufferedImage.TYPE_INT_RGB);
            Graphics2D theGraphics = (Graphics2D) theImage.getGraphics();
            theGraphics.setBackground(new Color(255, 255, 255, 0));
            theGraphics.clearRect(0, 0, theImage.getWidth(), theImage.getHeight());

            PDFRenderer theRenderer = new PDFRenderer(theDocument);
            theRenderer.renderPageToGraphics(0, theGraphics, theScaling);

            int rotation = theFirstPage.getRotation();
            if ((rotation == 90) || (rotation == 270)) {
                int w = theImage.getWidth();
                int h = theImage.getHeight();
                BufferedImage rotatedImg = new BufferedImage(w, h, theImage.getType());
                Graphics2D g = rotatedImg.createGraphics();
                g.rotate(Math.toRadians(rotation), w / 2, h / 2);
                g.drawImage(theImage, null, 0, 0);
            }
            theGraphics.dispose();
            return new Preview(theImage);
        } catch (Exception e) {
            LOGGER.error("Error creating preview for " + aFile, e);
            return null;
        }
    }

    @Override
    public boolean supportsFile(File aFile) {
        for (SupportedDocumentType theType : suppportedDocumentTypes) {
            if (theType.matches(aFile)) {
                return true;
            }
        }
        return false;
    }
}