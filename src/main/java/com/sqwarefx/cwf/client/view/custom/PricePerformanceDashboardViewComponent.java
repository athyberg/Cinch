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

import com.google.gwt.core.client.JsDate;
import com.sqwarefx.cwf.client.perspective.Chart;
import com.sqwarefx.cwf.client.perspective.Serie;
import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.JQuery;

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.GlobalScope;
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
                     + "&nbsp;<span #title_indicator class='glyphicon' aria-hidden='true'></span></div>"
                     + "<div class='panel-body'>"
                     + "<div class='row no-margin'>"
                     + "<div style='padding:0 10px'>"
                     + "<table class='table table-condensed summary no-margin'>"
                     + "<tr>"
                     + "<td class='small'>{{ \\\"relative\\\" | translate }}</td>"
                     + "<td class='text-right small'>{{ \\\"1d\\\" | translate }}</td><td><div class='label label-success'>+2.5%</div></td>"
                     + "<td class='text-right small'>{{ \\\"1wk\\\" | translate }}</td><td><div class='label'>+3.6%</div></td>"
                     + "<td class='text-right small'>{{ \\\"1mth\\\" | translate }}</td><td><div class='label'>+1.2%</div></td>"
                     + "</tr>"
                     + "<tr>"
                     + "<td class='small'>{{ \\\"absolute\\\" | translate }}</td>"
                     + "<td class='text-right small'>{{ \\\"1d\\\" | translate }}</td><td><span class='label'>+3.2%</span></td>"
                     + "<td class='text-right small'>{{ \\\"1wk\\\" | translate }}</td><td><span class='label'>+4.4%</span></td>"
                     + "<td class='text-right small'>{{ \\\"1mth\\\" | translate }}</td><td><span class='label'>+2.5%</span></td>"
                     + "</tr>"
                     + "</table>"

                     + "<table class='table table-condensed today no-margin'>"
                     + "<tr>"
                     + "<td class='small'>{{ \\\"today\\\" | translate }}</td>"
                     + "<td class='small'>{{ \\\"current\\\" | translate }}&nbsp;<div class='label'>{{ gbpValue }}</div></td>"
                     + "<td class='small'>{{ \\\"50d-mov-avg\\\" | translate }}&nbsp;<div class='label'>1.78</div></td>"
                     + "<td class='small'>{{ \\\"200d-mov-avg\\\" | translate }}&nbsp;<div class='label'>1.78</div></td>"
                     + "</tr>"
                     + "</table>"
                     + "</div>"
                     
                     + "<cwf-chart [model]='options'></cwf-chart>"
                     
                     + "<div class='zoom pull-right'>"
                     + "{{ \\\"zoom\\\" | translate }}&nbsp;"
                     + "<div class='btn-group'>"
                     + "<a class='btn btn-xs btn-default'>&nbsp;1M&nbsp;</a>"
                     + "<a class='btn btn-xs btn-default'>&nbsp;3M&nbsp;</a>"
                     + "<a class='btn btn-xs btn-default'>&nbsp;6M&nbsp;</a>"
                     + "<a class='btn btn-xs btn-primary'>&nbsp;1Y&nbsp;</a>"
                     + "<a class='btn btn-xs btn-default'>&nbsp;2Y&nbsp;</a>"
                     + "<a class='btn btn-xs btn-default'>&nbsp;Max&nbsp;</a></div></div>"
                     
                     + "</div>"
                     + "</div>"
                     + "</div>",
              styles = ".table {"
                     + "  table-layout: fix-ed;"
                     + "}"
                     + ".table > tbody > tr > td { "
                     + "  vertical-align: text-top;"
                     + "  padding: 2px;"
                     + "}"
                     + ".zoom {"
                     + "  padding: 5px;"
                     + "}" )

@JsType
public class PricePerformanceDashboardViewComponent extends DashboardViewComponent {

    @JsProperty @ViewChild( selector="title_indicator" ) 
    protected ElementRef titleIndicator;

    @JsProperty protected double gbpValue = 1.5;
    protected int dayOfMonth;
    

    public PricePerformanceDashboardViewComponent(PerspectiveService perspectiveService, 
        ViewEventDispatchService dispatch, DictionaryPipe dictionary,  ChangeDetectorRef changeDetectorRef, 
        ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }

    @Override
    protected void setViewSize(int height, int width) {
        super.setViewSize(height, width);
        
        int headingHeight = getOuterHeight(".panel-heading", false);
        int totBorderWidth = getTotalLRBorderWidth(".panel-default");
        int summaryHeight = getOuterHeight("table.summary", true); 
        int todayHeight = getOuterHeight("table.today", true); 
        int zoomHeight = getOuterHeight(".zoom", true); 
        
        chart.setSize(height - headingHeight - summaryHeight - todayHeight - zoomHeight - EXTRA_PADDING, 
            width - totBorderWidth);
    }
    
    @Override
    protected void getChartOptions() {
        options.set("chartOptions", JSON.parse("{"
            + "\"chart\": {"
            + "  \"zoomType\": \"x\","
            + "  \"marginLeft\": 10"
            + "},"
            + "\"title\": {\"text\": null},"
            + "\"xAxis\": {"
            + "  \"type\": \"datetime\""
            + "},"
            + "\"yAxis\": {"
            + "  \"title\": {"
            + "    \"text\": null"
            + "  },"
            + "\"opposite\": true"
            + "},"
            + "\"legend\": {"
            + "  \"enabled\": false"
            + "},"
            + "\"plotOptions\": {"
            + "  \"area\": {"
            + "    \"fillColor\": {"
            + "      \"linearGradient\": {"
            + "        \"x1\": 0,"
            + "        \"y1\": 0,"
            + "        \"x2\": 0,"
            + "        \"y2\": 1"
            + "      },"
            + "      \"stops\": ["
            + "        [0, \"rgba(255, 255, 255, 0.1)\"],"
            + "        [1, \"rgba(255, 255, 255, 0)\"]"
            + "      ]"
            + "    },"
            + "    \"marker\": false,"
            + "    \"lineWidth\": 1,"
            + "    \"states\": {"
            + "      \"hover\": {"
            + "        \"lineWidth\": 1"
            + "      }"
            + "    },"
            + "    \"threshold\": null"
            + "  }"
            + "},"
            + "\"series\": [{"
            + "  \"type\": \"area\","
            + "  \"name\": \"GBP\","
            + "  \"data\": []"
            + "}],"
            + "\"credits\": false}"));
        
        if (titleIndicator != null) {
            JQuery.Helper.$(titleIndicator.nativeElement()).addClass("glyphicon-arrow-up text-success");
        }
    }

    @Override
    public void ngAfterViewInit() {
        Chart c = chart.getChart();
        JsArray<Serie> series = c.getSeries();
        final Serie s = series.get(0);
                        
        GlobalScope.setInterval(() -> {
            gbpValue += Math.random() > .5 ? .01  : -.01;
            dayOfMonth += 1;
            s.addPoint(JsArray.of(JsDate.UTC(2010, 10, dayOfMonth, 0, 0, 0, 0), gbpValue), true, false, false);
        }, 1000);
    }
            
}
