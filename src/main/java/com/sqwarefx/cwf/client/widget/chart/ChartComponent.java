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
package com.sqwarefx.cwf.client.widget.chart;

import com.google.gwt.core.shared.GWT;
import com.sqwarefx.cwf.client.perspective.Chart;
import com.sqwarefx.cwf.client.widget.WidgetBase;

import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@Component( selector = "cwf-chart", 
            template = "<div #chart></div>" )

@JsType
public class ChartComponent extends WidgetBase {

    @ViewChild(selector = "chart")
    public ElementRef chartElement;
    
    @Input
    @JsProperty
    protected JsObject model;
    
    protected Chart chart;

    public ChartComponent(ChangeDetectorRef changeDetectorRef, 
        ElementRef elementRef) {
        super(changeDetectorRef, elementRef);
    }

    @Override
    public void ngAfterViewInit() {
        JsObject opts = model.get("chartOptions");
        if (chartElement.nativeElement() != null) {
            ((JsObject) opts.get("chart")).set("renderTo", chartElement.nativeElement());
            chart = new Chart(opts);
        }

    }
    
    @Override
    public void setSize(int height, int width) {
        super.setSize(height, width);
        chart.setSize(width, height, false);
    }

    public Chart getChart() {
        return chart;
    }
    
    public void update(JsObject options) {
        GWT.debugger();
        chart.update(options, true);
    }
    
    public void destroy() {
        
    }
    
}
