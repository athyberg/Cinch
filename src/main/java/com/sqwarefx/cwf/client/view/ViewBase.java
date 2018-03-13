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

import com.google.gwt.core.client.Scheduler;
import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.Logger;
import com.sqwarefx.cwf.client.widget.WidgetBase;

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnDestroy;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * Base class for all view components
 * 
 */
@JsType
public abstract class ViewBase extends WidgetBase implements OnInit, OnDestroy {

    @Input
    @JsProperty
    protected UiView model;
    
    @HostBinding("class")
    @JsProperty
    protected String className; 

    @HostBinding("attr.name")
    @JsProperty
    protected String name; 

    @HostBinding("attr.id")
    @JsProperty
    protected String id; 

    @JsProperty
    protected int viewHeight;

    @JsProperty
    protected int viewWidth;

    protected String perspectiveId; 
    protected String slotId; 

    protected PerspectiveService perspectiveService;
    protected ViewEventDispatchService dispatch;
    protected DictionaryPipe dictionary;

    protected ViewFactory factory;
    
    public ViewBase(PerspectiveService perspectiveService, 
                    ViewEventDispatchService dispatch,
                    DictionaryPipe dictionary,
                    ChangeDetectorRef changeDetectorRef,
                    ElementRef elementRef) {
        super(changeDetectorRef, elementRef);
        this.perspectiveService = perspectiveService;
        this.dispatch = dispatch;
        this.dictionary = dictionary;
    }    
    
    @Override
    public void ngOnInit() {
        this.id = model.getId();
        this.className = "view view-" + model.getViewType() + " " + model.getViewId()
            + (model.isActive() ? " active" : "");
        String additionalClassName = getAdditionalClassName();
        this.className += additionalClassName != null ? " " + additionalClassName : "";
        setName(dictionary.translate(model.getViewId()));
        Scheduler.get().scheduleDeferred(() -> registerViewEventHandler());
    }
    
    @Override
    public void ngOnDestroy() {
        Logger.log("ngOnDestroy: " + id);
    }
    
    protected void onViewAttach(JsObject data) {
        Logger.log("onViewAttach: " + JSON.stringify(data));
        if (model.isAttached()) {
            Logger.warn("Trying to attach already attached view!");
            return;
        }
        dispatch.fireEvent("view-attach", model, data);
    }

    protected void onViewAttached(JsObject data) {
        this.perspectiveId = data.get("perspectiveId");
        this.slotId = data.get("slotId");
        Logger.log("onViewAttached: " + JSON.stringify(data) 
            + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
            + (this.slotId != null ? " - " + this.slotId : ""));
    }

    protected void onViewActive(JsObject data) {
        Logger.log("onViewActive: " + JSON.stringify(data)
            + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
            + (this.slotId != null ? " - " + this.slotId : ""));
    }

    protected void onViewClose(JsObject data) {
        Logger.log("onViewClose: " + JSON.stringify(data)
            + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
            + (this.slotId != null ? " - " + this.slotId : ""));
        data.set("slotId", this.slotId);
        dispatch.fireEvent("view-close", model, data);
    }

    protected void onViewDetach(JsObject data) {
        Logger.log("onViewDetach: " + JSON.stringify(data)
            + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
            + (this.slotId != null ? " - " + this.slotId : ""));
        if (!model.isAttached()) {
            Logger.warn("Trying to detach already detached view!");
            return;
        }
        dispatch.fireEvent("view-detach", model, data);
    }

    protected void onViewResize(JsObject data) {
        Logger.log("onViewResize: " + JSON.stringify(data)
            + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
            + (this.slotId != null ? " - " + this.slotId : ""));
        setViewSize(getIntValue(data.get("height")), getIntValue(data.get("width")));
    }

    protected void onViewMinimize(JsObject data) {
        Logger.log("onViewMinimize: " + JSON.stringify(data)
        + (this.perspectiveId != null ? " - " + this.perspectiveId : "")
        + (this.slotId != null ? " - " + this.slotId : ""));
        if (model.isAttached()) {
            Logger.warn("Trying to minimize and attach already attached view!");
            return;
        }
        data.set("slotId", "footer");
        dispatch.fireEvent("view-attach", model, data);
    }
    
    protected void setViewSize(int height, int width) {
        viewHeight = height;
        viewWidth = width;
        if (!model.isAttached()) {
            get$().height(viewHeight);
            get$().width(viewWidth);
        }
    }
    
    protected void setName(String name) {
        Logger.log("setName: " + slotId + " - " + id + " - " + name);
        this.name = name;
    }
    
    @Override
    protected int getIntValue(Object value) {
        return ((Integer) value).intValue();
    }
    
    protected void registerViewEventHandler() {
        get$().on("view-attach view-attached view-active view-close view-detach view-resize view-minimize", (e, d) -> {
            JsObject data = (JsObject) d;
            assert this.id.equals(data.get("viewId"));
            if (e.type.equals("view-active")) {
                onViewActive(data); 
            }
            else if (e.type.equals("view-close")) {
                onViewClose(data); 
            }
            else if (e.type.equals("view-detach")) {
                onViewDetach(data); 
            }
            else if (e.type.equals("view-attach")) {
                onViewAttach(data); 
            }
            else if (e.type.equals("view-attached")) {
                onViewAttached(data); 
            }
            else if (e.type.equals("view-resize")) {
                onViewResize(data); 
            }
            else if (e.type.equals("view-minimize")) {
                onViewMinimize(data); 
            }
        });
    }

    protected String getAdditionalClassName() {
        return null;
    }
        
}
