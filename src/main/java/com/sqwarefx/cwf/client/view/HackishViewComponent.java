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
package com.sqwarefx.cwf.client.view;

import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;

import fr.lteconsulting.angular2gwt.client.interop.GlobalScope;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * View component
 * 
 */
@Component( selector = "cwf-custom-view", 
            template = "<div><div><b>{{name}}:</b><br>"
                     + "<i>ViewId:</i> {{id}}<br>"
                     + "<i>Attached:</i> {{model.attached}}<br>"
                     + "<i>ViewType:</i> {{model.viewType}}<br>"
                     + "</div>"
                     + "This is a custom view with a counter: <ng-container *ngIf='hackishModel'>{{hackishModel.cnt}}</ng-container><br>"
                     + "Size: {{viewHeight}} x {{viewWidth}} px...</div>" )

@JsType
public class HackishViewComponent extends ViewBase {
  
    @JsProperty
    protected UiView hackishModel;
    
    protected int intervalId;

    public HackishViewComponent(PerspectiveService perspectiveService, 
                         ViewEventDispatchService dispatch,
                         DictionaryPipe dictionary,
                         ChangeDetectorRef changeDetectorRef,
                         ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }
    
    @Override
    public void ngOnInit() {
        super.ngOnInit();
        hackishModel = model;
        
//        intervalId = GlobalScope.setInterval(() -> {
//            int cnt = ((Number) JsTools.getObjectProperty(HackishViewComponent.this.hackishModel, "cnt")).intValue();
//            setName(dictionary.translate(model.getViewId() + " - " + cnt++));
//            JsTools.setObjectProperty(model, "cnt", cnt);
//            changeDetectorRef.detectChanges();
//        }, 30000);
        
    }
        
    @Override
    public void ngOnDestroy() {
        super.ngOnDestroy();
        GlobalScope.clearInterval(intervalId);
    }
    
}