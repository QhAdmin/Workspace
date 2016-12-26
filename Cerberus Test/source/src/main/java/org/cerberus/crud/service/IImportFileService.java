/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.crud.service;

import java.io.InputStream;
import org.cerberus.util.answer.AnswerItem;

/**
 * Interface for service that handles requests for importing external files.
 * @author FNogueira
 */
public interface IImportFileService {

    public enum XMLHandlerEnumType {TESTDATALIB_HANDLER};
    
    /**
     * Imports an XML file - returns the Document object
     * @param filecontent
     * @param schemaContent
     * @param handlerType
     * @return an answer containing the status of the operation and the XML document that was imported.
     */
    AnswerItem importAndValidateXMLFromInputStream(InputStream  filecontent, InputStream schemaContent, XMLHandlerEnumType handlerType); 
}
