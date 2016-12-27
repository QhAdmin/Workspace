/*
 *  Copyright (c) 2011, StripBandunk and/or its affiliates. All rights reserved.
 * 
 *       http://stripbandunk.com/
 * 
 *  STRIPBANDUNK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.github.khannedy.simplepos.view;

import java.awt.Window;

/**
 *
 * @author Eko Kurniawan Khannedy
 */
public interface View {

    /**
     * fire on view is displaying
     *
     * @param formApp
     * @param parameter
     */
    void display(Window formApp, Object parameter);
}
