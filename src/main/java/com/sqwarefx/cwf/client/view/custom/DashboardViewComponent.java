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
import com.sqwarefx.cwf.client.view.ViewBase;
import com.sqwarefx.cwf.client.widget.chart.ChartComponent;

import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
*
* View component
* 
*/
@JsType
public class DashboardViewComponent extends ViewBase {

    protected static final int EXTRA_PADDING = 4;
    
    @ViewChild( component = ChartComponent.class )
    @JsProperty
    protected ChartComponent chart;
    
    @JsProperty
    protected JsObject options = new JsObject();
        
    public DashboardViewComponent(PerspectiveService perspectiveService, ViewEventDispatchService dispatch,
        DictionaryPipe dictionary, ChangeDetectorRef changeDetectorRef, ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }

    @Override
    public void ngOnInit() {
        super.ngOnInit();
        getChartOptions();
    }
    
    @Override
    public void ngOnDestroy() {
        super.ngOnDestroy();
        chart.destroy();
    }

    @Override
    protected void setViewSize(int height, int width) {
        int headingHeight = getOuterHeight(".panel-heading", false);
        int totBorderWidth = getTotalLRBorderWidth(".panel-default");
        super.setViewSize(height - headingHeight - EXTRA_PADDING, width);
        chart.setSize(height - headingHeight - EXTRA_PADDING, width - totBorderWidth);
    }
        
    protected void getChartOptions() {
    }

}
