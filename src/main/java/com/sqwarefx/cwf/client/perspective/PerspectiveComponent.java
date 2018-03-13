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

import com.google.gwt.core.client.Scheduler;
import com.sqwarefx.cwf.client.entity.UiPerspective;
import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.entity.event.PerspectiveLayoutChangeEvent;
import com.sqwarefx.cwf.client.transport.Bootstrap;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.util.Logger;
import com.sqwarefx.cwf.client.widget.WidgetBase;

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Subscription;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.router.ActivatedRoute;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * Perspective component
 * 
 */
@Component(selector = "cwf-perspective", 
           template = "<div *ngIf='(progress < 100)' class='progress'>{{ progress }}%</div>" 
                    + "<ng-container *ngIf='perspective'><cwf-slot [root]='perspective.root'></cwf-slot></ng-container>" 
                    + "<cwf-dialog *ngFor='let model of viewModels' [model]='model'></cwf-dialog>" )
@JsType
public class PerspectiveComponent extends WidgetBase {

    @ViewChild(component = DialogComponent.class)
    @JsProperty
    protected DialogComponent dialogComponent;
    
    @HostBinding("attr.id")
    @JsProperty
    protected String perspectiveId;

    @HostBinding("class")
    @JsProperty
    protected String className = "perspective";
    
    @JsProperty
    protected JsArray<UiView> viewModels = new JsArray<>();
    
    @JsProperty
    protected UiPerspective perspective;
    
    protected ActivatedRoute route;
    protected PerspectiveService perspectiveService;
    protected ViewEventDispatchService dispatch;
    protected Subscription routeParamSubscr;
    protected Subscription bootProgSubscr;
    protected Subscription viewEventSubscr;
    protected Bootstrap bootstrap;

    @JsProperty
    protected int progress;
    
    public PerspectiveComponent(Bootstrap bootstrap,
                                PerspectiveService perspectiveService, 
                                ActivatedRoute route, 
                                ViewEventDispatchService dispatch, 
                                ChangeDetectorRef changeDetectorRef,
                                ElementRef elementRef) {
        super(changeDetectorRef, elementRef);
        this.bootstrap = bootstrap;
        this.perspectiveService = perspectiveService;
        this.route = route;
        this.dispatch = dispatch;
    }

    @Override
    public void ngOnInit() {
        if (bootstrap.isReady()) {
            init();
            return;
        }
        bootProgSubscr = bootstrap.progress.subscribe(p -> {
            progress = p;
            if (p == 100) {
                init();
            }
        });
    }

    @Override
    public void ngOnDestroy() {
        Logger.log("ngOnDestroy: " + perspectiveId);
        if (bootProgSubscr != null ) {
            bootProgSubscr.unsubscribe();
        }
        routeParamSubscr.unsubscribe();
        viewEventSubscr.unsubscribe();
    }
    
    protected void init() {
        progress = 100;
        routeParamSubscr = route.params.subscribe(params -> {
            perspectiveId = params.get("perspectiveId");
            if (perspectiveId != null) {
                perspective = perspectiveService.getPerspective(perspectiveId);
                Scheduler.get().scheduleDeferred(() -> { 
                    BootstrapPerspective.layout();
                    registerLayoutChangeHandler();
                });
            }
        });  
                
        viewEventSubscr = dispatch.observable.subscribe(e -> {
            if (e.eventType.equals("view-close") 
                || e.eventType.equals("view-attach") 
                || e.eventType.equals("view-minimize")) {
                spliceView(e.model);
            }
            else if (e.eventType.equals("view-detach")) {
                viewModels.push(e.model);
                changeDetectorRef.detectChanges();
                Scheduler.get().scheduleDeferred(() -> {
                    JsObject options = new JsObject();
                    options.set("top", e.data.get("top"));
                    options.set("left", e.data.get("left"));
                    options.set("height", e.data.get("height"));
                    options.set("width", e.data.get("width"));
                    JQuery.Helper.$("#" + e.model.getId()).detatch(options);
                });
            }
        });  
    }

    protected void spliceView(UiView model) {
        int idx = viewModels.indexOf(model);
        if (idx != -1) {
            viewModels.splice(idx, 1);
            dialogComponent.removeView(model);
            changeDetectorRef.detectChanges();
        }
    }

    protected void onLayoutChange(PerspectiveLayoutChangeEvent change) {
        Logger.log("onLayoutChange: " + JSON.stringify(change));
        perspectiveService.updatePerspective(change);
    }
    
    protected void registerLayoutChangeHandler() {
        get$().on("layout-change", (e, d) -> onLayoutChange((PerspectiveLayoutChangeEvent) d));
    }

}