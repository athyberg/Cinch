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

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.QueryList;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.ViewChildren;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
*
* View component
* 
*/
@Component( selector = "cwf-custom-view", 
            template = "<div class='panel panel-default'>"
                     + "<div class='panel-heading'>{{ model.viewId | translate }}</div>"
                     + "<div class='panel-body'>"
                     + "<div class='row no-margin'>"
                     + "<div class='col-sm-6 no-padding'><cwf-chart [model]='ownershipStyle'></cwf-chart></div>"
                     + "<div class='col-sm-6 no-padding'><cwf-chart [model]='ownershipCountry'></cwf-chart></div>"
                     + "</div>"
                     + "</div>"
                     + "</div>" )

@JsType
public class OwnershipDashboardViewComponent extends ViewBase {

    @ViewChildren( component = ChartComponent.class )
    @JsProperty
    protected QueryList<ChartComponent> charts;
    
    @JsProperty
    protected JsObject ownershipStyle = new JsObject();

    @JsProperty
    protected JsObject ownershipCountry = new JsObject();
    
    public OwnershipDashboardViewComponent(PerspectiveService perspectiveService, 
                                           ViewEventDispatchService dispatch,
                                           DictionaryPipe dictionary, 
                                           ChangeDetectorRef changeDetectorRef, 
                                           ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }

    @Override
    public void ngOnInit() {
        super.ngOnInit();
        ownershipStyle.set("chartOptions", JSON.parse("{"
            + "\"chart\": {"
            + "    \"plotBackgroundColor\": null,"
            + "    \"plotBorderWidth\": null,"
            + "    \"plotShadow\": false,"
            + "    \"marginTop\": 15,"
            + "    \"marginLeft\": 10,"
            + "    \"marginRight\": 70,"
            + "    \"type\": \"pie\""
            + "},"
            + "\"plotOptions\": {"
            + "    \"pie\": {"
            + "        \"innerSize\": \"80%\","
            + "        \"dataLabels\": {"
            + "            \"enabled\": false"
            + "        },"
            + "        \"showInLegend\": true,"
            + "        \"borderWidth\": 0"
            + "    }"
            + "},"
            + "\"title\": {"
            + "    \"text\": \"Style\","
            + "    \"x\": 0,"
            + "    \"align\": \"left\""
            + "},"
            + "\"legend\": {"
            + "    \"align\": \"right\","
            + "    \"verticalAlign\": \"bottom\","
            + "    \"layout\": \"vertical\","
            + "    \"symbolWidth\": 5,"
            + "    \"itemStyle\": {"
            + "        \"fontSize\": \"10px\","
            + "        \"fontWeight\": 0,"
            + "        \"textOverflow\": \"ellipsis\""
            + "    },"
            + "    \"width\": 70"
            + "},"
            + "\"tooltip\": {"
            + "    \"enabled\": false"
            + "},"
            + "\"series\": [{"
            + "    \"data\": ["
            + "        [\"Core value\", 2262],"
            + "        [\"Core growth\", 3800],"
            + "        [\"Income\", 1000],"
            + "        [\"Index\", 1986],"
            + "        [\"GARP\", 865]"
            + "    ]"
            + "}],"
            + "\"credits\": false"
            + "}"));

        ownershipCountry.set("chartOptions", JSON.parse("{"
            + "\"chart\": {"
            + "    \"plotBackgroundColor\": null,"
            + "    \"plotBorderWidth\": null,"
            + "    \"plotShadow\": false,"
            + "    \"marginTop\": 15,"
            + "    \"marginLeft\": 10,"
            + "    \"marginRight\": 70,"
            + "    \"type\": \"pie\""
            + "},"
            + "\"plotOptions\": {"
            + "    \"pie\": {"
            + "        \"innerSize\": \"80%\","
            + "        \"dataLabels\": {"
            + "            \"enabled\": false"
            + "        },"
            + "        \"showInLegend\": true,"
            + "        \"borderWidth\": 0"
            + "    }"
            + "},"
            + "\"title\": {"
            + "    \"text\": \"Country\","
            + "    \"x\": 0,"
            + "    \"align\": \"left\""
            + "},"
            + "\"legend\": {"
            + "    \"align\": \"right\","
            + "    \"verticalAlign\": \"bottom\","
            + "    \"layout\": \"vertical\","
            + "    \"symbolWidth\": 5,"
            + "    \"itemStyle\": {"
            + "        \"fontSize\": \"10px\","
            + "        \"fontWeight\": 0,"
            + "        \"textOverflow\": \"ellipsis\""
            + "    },"
            + "    \"width\": 70"
            + "},"
            + "\"tooltip\": {"
            + "    \"enabled\": false"
            + "},"
            + "\"series\": [{"
            + "    \"data\": ["
            + "        [\"UK\", 1000],"
            + "        [\"USA\", 2000],"
            + "        [\"Germany\", 3000],"
            + "        [\"Norway\", 4000]"
            + "    ]"
            + "}],"
            + "\"credits\": false"
            + "}"));

    }
    
    @Override
    protected void setViewSize(int height, int width) {
        super.setViewSize(height, width);
        int headingHeight = getOuterHeight(".panel-heading", false);
        int totBorderWidth = getTotalLRBorderWidth(".panel-default");
        charts.forEach( c -> c.setSize(height - headingHeight - DashboardViewComponent.EXTRA_PADDING, 
            (width - totBorderWidth) / 2) );
    }
    
    @Override
    public void ngOnDestroy() {
        super.ngOnDestroy();
        charts.forEach( c -> c.destroy() );
    }
        
}
