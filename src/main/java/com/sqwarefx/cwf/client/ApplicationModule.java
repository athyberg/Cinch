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
package com.sqwarefx.cwf.client;


import com.sqwarefx.cwf.client.navigation.NavbarComponent;
import com.sqwarefx.cwf.client.navigation.Routes;
import com.sqwarefx.cwf.client.perspective.DialogComponent;
import com.sqwarefx.cwf.client.perspective.PerspectiveComponent;
import com.sqwarefx.cwf.client.perspective.SlotComponent;
import com.sqwarefx.cwf.client.perspective.ViewFactoryOutputComponent;
import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.Bootstrap;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.view.DetailsViewComponent;
import com.sqwarefx.cwf.client.view.FooterViewComponent;
import com.sqwarefx.cwf.client.view.FormViewComponent;
import com.sqwarefx.cwf.client.view.HackishViewComponent;
import com.sqwarefx.cwf.client.view.TableViewComponent;
import com.sqwarefx.cwf.client.view.TreeViewComponent;
import com.sqwarefx.cwf.client.view.ViewFactory;
import com.sqwarefx.cwf.client.view.custom.CdsDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.CorrelationDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.DirectorDealingsDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.EpsMomentumDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.EventsDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.ImpliedVolatilityDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.NewsDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.OwnershipDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.PriceActionDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.PricePerformanceDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.ShortInterestDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.ValuationDashboardViewComponent;
import com.sqwarefx.cwf.client.view.custom.VolumeDashboardViewComponent;
import com.sqwarefx.cwf.client.widget.ContextMenuComponent;
import com.sqwarefx.cwf.client.widget.HorizontalScrollbarComponent;
import com.sqwarefx.cwf.client.widget.PopupPanelComponent;
import com.sqwarefx.cwf.client.widget.TableComponent;
import com.sqwarefx.cwf.client.widget.TableToolbarComponent;
import com.sqwarefx.cwf.client.widget.TreeComponent;
import com.sqwarefx.cwf.client.widget.VerticalScrollbarComponent;
import com.sqwarefx.cwf.client.widget.chart.ChartComponent;

import fr.lteconsulting.angular2gwt.client.interop.ng.forms.FormsModule;
import fr.lteconsulting.angular2gwt.client.interop.ng.http.HttpModule;
import fr.lteconsulting.angular2gwt.client.interop.ng.platformBrowser.BrowserModule;
import fr.lteconsulting.angular2gwt.ng.core.NgModule;
import jsinterop.annotations.JsType;

/**
 *
 * The CWF application module declarations
 * 
 */
@NgModule(  

    imports = {      
        BrowserModule.class, 
        FormsModule.class,
        HttpModule.class,
        Routes.class 
    }, 

    declarations = { 
        ApplicationComponent.class, 
        NavbarComponent.class,
        PerspectiveComponent.class,
        SlotComponent.class,
        TableViewComponent.class,
        TreeViewComponent.class,
        FormViewComponent.class,
        DetailsViewComponent.class,
        FooterViewComponent.class,
        HackishViewComponent.class,
        
        OwnershipDashboardViewComponent.class,
        PricePerformanceDashboardViewComponent.class,
        VolumeDashboardViewComponent.class,
        PriceActionDashboardViewComponent.class,
        DirectorDealingsDashboardViewComponent.class,
        ValuationDashboardViewComponent.class,
        ValuationDashboardViewComponent.class,
        CorrelationDashboardViewComponent.class,

        EpsMomentumDashboardViewComponent.class,
        ImpliedVolatilityDashboardViewComponent.class,
        ShortInterestDashboardViewComponent.class,
        CdsDashboardViewComponent.class,
        NewsDashboardViewComponent.class,
        EventsDashboardViewComponent.class,
        
        DialogComponent.class,
        TableComponent.class,
        TreeComponent.class,
        TableToolbarComponent.class,
        ViewFactoryOutputComponent.class,
        VerticalScrollbarComponent.class,
        HorizontalScrollbarComponent.class,
        
        ContextMenuComponent.class,
        
        PopupPanelComponent.class,

        ChartComponent.class,
        
        DictionaryPipe.class
        
    },
    
    entryComponents = {
        TableViewComponent.class,
        TreeViewComponent.class,
        FormViewComponent.class,
        DetailsViewComponent.class,
        FooterViewComponent.class,
        HackishViewComponent.class,
        OwnershipDashboardViewComponent.class,
        PricePerformanceDashboardViewComponent.class,
        VolumeDashboardViewComponent.class,
        PriceActionDashboardViewComponent.class,
        DirectorDealingsDashboardViewComponent.class,
        ValuationDashboardViewComponent.class,
        ValuationDashboardViewComponent.class,
        CorrelationDashboardViewComponent.class,
        EpsMomentumDashboardViewComponent.class,
        ImpliedVolatilityDashboardViewComponent.class,
        ShortInterestDashboardViewComponent.class,
        CdsDashboardViewComponent.class,
        NewsDashboardViewComponent.class,
        EventsDashboardViewComponent.class,
        
        ChartComponent.class,

        ViewFactoryOutputComponent.class
    },
    
    providers = {    
        Bootstrap.class,
        PerspectiveService.class,
        ViewEventDispatchService.class,
        DictionaryPipe.class,
        ViewFactory.class
    },

    bootstrap = ApplicationComponent.class
)

@JsType
public class ApplicationModule {

}
