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
package com.sqwarefx.cwf.client.view.custom;

import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.JQuery;

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
*
* View component
* 
*/
@Component( selector = "cwf-custom-view", 
            template = "<div class='panel panel-default'>"
                     + "<div class='panel-heading'>{{ model.viewId | translate }}"
                     + "<span #title_indicator>&nbsp;{{ valuation | translate }}</span></div>"
                     + "<div class='panel-body'>"
                     + "<div class='row no-margin'>"
                     + "<cwf-chart [model]='options'></cwf-chart>"
                     + "</div>"
                     + "</div>"
                     + "</div>" )

@JsType
public class ValuationDashboardViewComponent extends DashboardViewComponent {

    @ViewChild (selector="title_indicator")
    @JsProperty
    protected ElementRef titleIndicator;

    @JsProperty
    protected String valuation = "cheap";
    
    public ValuationDashboardViewComponent(PerspectiveService perspectiveService, ViewEventDispatchService dispatch,
        DictionaryPipe dictionary, ChangeDetectorRef changeDetectorRef, ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }

    @Override
    protected void getChartOptions() {
        options.set("chartOptions", JSON.parse("{\"chart\": {}, \"title\": {\"text\": null}, \"credits\": false}"));
        if (titleIndicator != null) {
            JQuery.Helper.$(titleIndicator.nativeElement()).addClass("text-success");
        }
    }    
        
}
