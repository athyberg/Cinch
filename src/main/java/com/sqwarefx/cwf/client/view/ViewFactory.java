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
import com.sqwarefx.cwf.client.entity.UiViewType;
import com.sqwarefx.cwf.client.view.custom.CdsDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.CorrelationDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.DirectorDealingsDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.EpsMomentumDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.EventsDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.ImpliedVolatilityDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.NewsDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.OwnershipDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.PriceActionDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.PricePerformanceDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.ShortInterestDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.ValuationDashboardViewComponent_AngularComponent;
import com.sqwarefx.cwf.client.view.custom.VolumeDashboardViewComponent_AngularComponent;

import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentFactory;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentFactoryResolver;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ViewContainerRef;
import fr.lteconsulting.angular2gwt.ng.core.Injectable;
import jsinterop.annotations.JsType;

/**
 *
 * The view factory
 * 
 */
@Injectable
@JsType
public class ViewFactory {

    public ComponentRef createView(UiView model, ComponentFactoryResolver resolver, ViewContainerRef containerRef) {

        if (UiViewType.table.equals(model.getViewType())) {
            return createComponent(resolver, containerRef, model, 
                TableViewComponent_AngularComponent.getComponentPrototype());
        }
        
        if (UiViewType.tree.equals(model.getViewType())) {
            return createComponent(resolver, containerRef, model, 
                TreeViewComponent_AngularComponent.getComponentPrototype());
        }
        
        if (UiViewType.form.equals(model.getViewType())) {
            return createComponent(resolver, containerRef, model, 
                FormViewComponent_AngularComponent.getComponentPrototype());
        }
        
        if (UiViewType.details.equals(model.getViewType())) {
            return createComponent(resolver, containerRef, model, 
                DetailsViewComponent_AngularComponent.getComponentPrototype());
        }
        
        if (UiViewType.custom.equals(model.getViewType())) {
            
            if ("footer".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    FooterViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("hackish".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    HackishViewComponent_AngularComponent.getComponentPrototype());
            }

            if ("ownership".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    OwnershipDashboardViewComponent_AngularComponent.getComponentPrototype());
            }

            if ("price-performance".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    PricePerformanceDashboardViewComponent_AngularComponent.getComponentPrototype());
            }

            if ("volume".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    VolumeDashboardViewComponent_AngularComponent.getComponentPrototype());
            }

            if ("price-action".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    PriceActionDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("director-dealings".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    DirectorDealingsDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("valuation".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    ValuationDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("correlation".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    CorrelationDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("eps-momentum".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    EpsMomentumDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("implied-volatility".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    ImpliedVolatilityDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("short-interest".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    ShortInterestDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("cds".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    CdsDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("news".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    NewsDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
            
            if ("events".equals(model.getViewId())) {
                return createComponent(resolver, containerRef, model, 
                    EventsDashboardViewComponent_AngularComponent.getComponentPrototype());
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected <M extends UiView, V extends ViewBase> ComponentRef createComponent(ComponentFactoryResolver resolver, 
            ViewContainerRef containerRef, M model, Object prototype) {
        ComponentFactory factory = resolver.resolveComponentFactory(prototype);
        ComponentRef component = (ComponentRef) containerRef.createComponent(factory);
        V instance = (V) component.instance();
        instance.model = model;
        return component;
    }

}
