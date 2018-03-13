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

import com.sqwarefx.cwf.client.view.PopupPanelInfo;
import com.sqwarefx.cwf.client.view.ViewFactory;

import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentFactoryResolver;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ViewContainerRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@Component( selector = "cwf-popup-panel", 
            template = "<ng-container></ng-container>" )

@JsType
public class PopupPanelComponent implements OnInit {

    @Input
    @JsProperty
    protected PopupPanelInfo info;

    protected ViewContainerRef containerRef;
    protected ComponentFactoryResolver resolver;
    protected ViewFactory viewFactory;
    protected ComponentRef componentRef;

    public PopupPanelComponent(ViewContainerRef containerRef,
                               ComponentFactoryResolver resolver,
                               ViewFactory viewFactory) {
        this.containerRef = containerRef;
        this.resolver = resolver;
        this.viewFactory = viewFactory;
    }

    @Override
    public void ngOnInit() {
    }

}
