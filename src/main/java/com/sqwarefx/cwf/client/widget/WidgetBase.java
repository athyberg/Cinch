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
package com.sqwarefx.cwf.client.widget;

import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.interop.ng.core.AfterViewInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnChanges;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnDestroy;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.SimpleChanges;
import jsinterop.annotations.JsType;

/**
 *
 * Widget base component
 * 
 */
@JsType
public abstract class WidgetBase implements OnInit, OnDestroy, OnChanges, AfterViewInit {
    
    protected ChangeDetectorRef changeDetectorRef;
    protected ElementRef elementRef;
 
    public WidgetBase(ChangeDetectorRef changeDetectorRef, 
                      ElementRef elementRef) {
        this.changeDetectorRef = changeDetectorRef;
        this.elementRef = elementRef;
    }
    
    public void setSize(int height, int width) {
        get$().height(height);
        get$().width(width);
        changeDetectorRef.detectChanges();
    }
    
    @Override
    public void ngOnInit() {
    }
    
    @Override
    public void ngOnDestroy() {
    }
    
    @Override
    public void ngOnChanges(SimpleChanges changes) {
        Logger.log(changes);
    }

    @Override
    public void ngAfterViewInit() {
    }
    
    protected JQuery get$() {
        return JQuery.Helper.$(elementRef.nativeElement());
    }
    
    protected int getIntValue(Number value) {
        return value != null ? value.intValue() : 0;
    }

    protected int getIntValue(Object value) {
        return value != null ? ((Number) value).intValue() : 0;
    }
    
    protected int getOuterHeight(String selector, boolean includeMargin) {
        JQuery item = get$().find(selector);
        Number outerHeight = item.is(":visible") ? item.outerHeight(includeMargin) : 0;
        return getIntValue(outerHeight);
    }

    protected int getOuterWidth(String selector, boolean includeMargin) {
        Number outerWidth = get$().find(selector).outerWidth(includeMargin);
        return getIntValue(outerWidth);
    }
    protected int getTotalLRBorderWidth(String selector) {
        JQuery element = get$().find(selector);
        return getIntValue(element.outerWidth()) - getIntValue(element.innerWidth());
    }
    
}