/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 Sqwarefx AB.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sqwarefx.server.entity;

import jsinterop.annotations.JsMethod;


public class TableViewModel extends ViewModel {

    public int totalSize;
    public int viewportSize;
    public int position;
    
    public String[] headers;
    public int[] columnOrder;
    public boolean[] columnVisible;
    
    public TableViewModel(String viewId, boolean active, boolean attached) {
        super(viewId, ViewType.table, active, attached);

        // dummy data
        totalSize = (int) Math.floor(Math.random() * 400 + 1);
        
        int nbOfHeaders = (int) Math.floor(Math.random() * 10 + 1);
        headers = new String[nbOfHeaders];
        columnOrder = new int[nbOfHeaders];
        columnVisible = new boolean[nbOfHeaders];
        for (int i = 0; i < nbOfHeaders; i++) {
            headers[i] = "Header " + i;
            columnOrder[i] = i;
            columnVisible[i] = true;
        }
    }
    
    @JsMethod
    public int getTotalSize() {
        return totalSize;
    }
}
