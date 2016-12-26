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

import java.util.List;
import org.cerberus.crud.entity.Documentation;
import org.json.JSONObject;

/**
 * @author bcivel
 */
public interface IDocumentationService {

    String findLabel(String docTable, String docField, String defaultLabel, String lang);
    
    String findLabelHTML(String docTable, String docField, String defaultLabel, String lang);

    List<Documentation> findDocumentationsWithNotEmptyValueAndDescription(String docTable, String docField, String lang);

    List<Documentation> findDocumentationsWithEmptyValueAndNotEmptyDescription(String docTable, String docField, String lang);

    String findLabelFromTableAndField(String docTable, String docField, String lang);

    String findDescriptionFromTableFieldAndValue(String docTable, String docField, String docValue, String lang);

    List<Documentation> findAll(String lang);
    
    List<Documentation> findAllWithEmptyDocLabel(String lang);
    
    JSONObject formatGroupByDocTable(List<Documentation> docList);
}
