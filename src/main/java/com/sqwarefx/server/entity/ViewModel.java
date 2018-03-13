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

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 *
 * TODO! add view height amd width as properties so that dialogs gets an initial size
 * 
 */
@JsonTypeInfo(include=As.PROPERTY, use=Id.MINIMAL_CLASS)
public abstract class ViewModel {

    protected static int viewCnt = 1;
    
    public String id;
    
    public String viewId;

    public boolean active;

    public boolean attached; 
    
    public String viewType;

    
    public ViewModel(String viewId, String viewType, boolean active, boolean attached) {
        this.id = "view-" + viewCnt++;
        this.viewId = viewId;
        this.viewType = viewType;
        this.active = active;
        this.attached = attached;
    }
    
    public boolean isAttached() {
        return attached;
    }
        
    public void destroy() {
        // TODO remove from PerspectiveService
    }
    
}
