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
package com.sqwarefx.cwf.client.perspective;

import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * View component
 * 
 */
@Component( selector = "cwf-dialog", 
            template = "<cwf-viewfactory-output [viewModels]='getModelAsArray()'></cwf-viewfactory-output>" )

@JsType
public class DialogComponent implements OnInit {
    
    @JsIgnore
    protected static int dialogCnt = 1;

    @ViewChild(component = ViewFactoryOutputComponent.class)
    @JsProperty
    protected ViewFactoryOutputComponent viewFactoryOutput;
    
    @JsProperty
    @Input
    protected UiView model;

    @JsProperty
    @HostBinding("id")
    protected String dialogId = "dialog-" + dialogCnt++;

    @JsProperty
    @HostBinding("class")
    protected String className = "dialog";

    @JsMethod
    public JsArray<UiView> getModelAsArray() {
        return JsArray.of(model);
    }

    @Override
    public void ngOnInit() {
        Logger.log(model);
    }
    
    public void removeView(UiView model) {
        viewFactoryOutput.destroyView(model);
    }
    
}