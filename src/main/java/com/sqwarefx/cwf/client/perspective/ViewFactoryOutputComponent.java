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

import java.util.HashMap;
import java.util.Map;

import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.view.ViewFactory;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentFactoryResolver;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ComponentRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ViewContainerRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * View factory output component
 * 
 */
@Component( selector = "cwf-viewfactory-output", 
            template = "<ng-container></ng-container>" )

@JsType
public class ViewFactoryOutputComponent implements OnInit {

    @Input
    @JsProperty
    protected JsArray<UiView> viewModels;

    @HostBinding("class")
    @JsProperty
    protected String className = "view ignore"; 
    
    protected Map<String, ComponentRef> viewMap = new HashMap<>(); 
    
    protected ViewContainerRef containerRef;
    protected ComponentFactoryResolver resolver;
    protected ViewFactory viewFactory;


    public ViewFactoryOutputComponent(ViewContainerRef containerRef,
                                      ComponentFactoryResolver resolver,
                                      ViewFactory viewFactory) {
        this.containerRef = containerRef;
        this.resolver = resolver;
        this.viewFactory = viewFactory;
    }

    @Override
    public void ngOnInit() {
        for (int i = 0; viewModels != null && i < viewModels.length(); i++) {
            createView(viewModels.get(i));
        }
    }
    
    @JsMethod
    public void destroyView(UiView model) {
        ComponentRef componentRef = viewMap.get(model.getId());
        assert componentRef != null : "No component with " + model.getId() + " found!";
        componentRef.destroy();
    }

    @JsMethod
    public void createView(UiView model) {
        ComponentRef componentRef = viewFactory.createView(model, resolver, containerRef);
        viewMap.put(model.getId(), componentRef);
    }
        
}