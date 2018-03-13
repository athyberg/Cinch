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

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import jsinterop.annotations.JsIgnore;

/**
 *
 * Perspective Slot
 * 
 */
@JsonTypeInfo(include=As.PROPERTY, use=Id.MINIMAL_CLASS)
public class PerspectiveSlot implements SlotType {

    protected static int slotCnt = 1;
    
    public String slotId;
    public String style;
    public String slotType;
    public PerspectiveSlot[] children = new PerspectiveSlot[0];
    public ViewModel[] viewModels = new ViewModel[0];
    public String activeView;
    
    public String className;
    
    public PerspectiveSlot(String... params) {
        this.slotId = "slot-" + slotCnt++;
        if (params != null && params.length > 0) {
            this.slotType = params[0];
            this.className = createClassName(this.slotType);
        }
        if (params != null && params.length > 1) {
            if (params[1] != null && params[1].contains("fixed")) {
                this.className += " fixed";
                params[1] = params[1].replaceAll("fixed", "").trim();
            }
            this.style = params[1].isEmpty() ? null : params[1];
        }
        if (params != null && params.length > 2) {
            this.slotId = params[2];
        }
    }
    
    
    public PerspectiveSlot addChild(final PerspectiveSlot child) {
        ArrayList<PerspectiveSlot> c = new ArrayList<>(Arrays.asList(children));
        c.add(child);
        children = c.toArray(new PerspectiveSlot[c.size()]);
        return this;
    }

    
    public PerspectiveSlot addView(ViewModel model) {
        if (viewModels.length == 0) {
            activeView = model.id;
        }
        ArrayList<ViewModel> v = new ArrayList<>(Arrays.asList(viewModels));
        v.add(model);
        viewModels = v.toArray(new ViewModel[v.size()]);
        return this;
    }

    
    public void setStyle(String style) {
        this.style = style;
    }

    
    protected String createClassName(String slotType) {
        if (splitHorizontal.equals(slotType)) {
            return "slot slot-split slot-split-horizontal";
        }
        if (splitVertical.equals(slotType)) {
            return "slot slot-split slot-split-vertical";
        }
        if (single.equals(slotType)) {
            return "slot slot-single";
        }
        if (tab.equals(slotType)) {
            return "slot slot-tab";
        }
        if (tray.equals(slotType)) {
            return "slot slot-tray";
        }
        return "slot slot-undefined";
    }
        
    @JsIgnore
    protected boolean isFixed() {
        return "fixed".equals(this.style);
    }

}
