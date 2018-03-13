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
import com.sqwarefx.cwf.client.entity.UiSlot;
import com.sqwarefx.cwf.client.entity.UiSlotType;
import com.sqwarefx.cwf.client.entity.UiView;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Subscription;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnDestroy;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.platformBrowser.DomSanitizationService;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * Slot component
 * 
 */
@Component( selector = "cwf-slot", 
            template = "<div class='section' *ngIf='root'>"
                     + "<cwf-slot *ngFor='let slot of slots' [root]='slot' fade></cwf-slot>"
                     + "<cwf-viewfactory-output *ngIf='viewModels' [viewModels]=viewModels></cwf-viewfactory-output>"
                     + "</div>" )

@JsType
public class SlotComponent implements OnInit, OnDestroy, UiSlotType {

    @ViewChild(component = ViewFactoryOutputComponent.class)
    @JsProperty
    protected ViewFactoryOutputComponent viewFactoryOutput;
    
    @HostBinding("class")
    @JsProperty
    protected String className;

    @HostBinding("id")
    @JsProperty
    protected String slotId;

    @HostBinding("style.flex")
    @JsProperty
    protected String flex;

    @Input
    @JsProperty
    protected UiSlot root;

    @JsProperty
    protected JsArray<UiSlot> slots;

    @JsProperty
    protected JsArray<UiView> viewModels;

    protected String perspectiveId;
    
    protected PerspectiveService perspectiveService;
    protected DomSanitizationService sanitization;
    protected ViewEventDispatchService dispatch;
    protected Subscription dispatchSubscr;

    protected ChangeDetectorRef ref;
    
    public SlotComponent(PerspectiveService rpcService, 
                         DomSanitizationService s, 
                         ViewEventDispatchService dispatch,
                         ChangeDetectorRef ref) {
        this.perspectiveService = rpcService;
        this.sanitization = s;
        this.dispatch = dispatch;
        this.ref = ref;
    }
    
    @Override
    public void ngOnDestroy() {
        Logger.log("ngOnDestroy: " + slotId);
        dispatchSubscr.unsubscribe();
    }
    
    protected void setStyle(String style) {
        if (style == null) {
            flex = null;
        }
        else if (style.startsWith("flex:")) {
            flex = style.substring(5).replace(';', ' ').trim();
        }
        else {
            flex = "0 0 " + style;
        }
    }
    
    @Override
    public void ngOnInit() {
        className = root.getClassName();
        slotId = root.getSlotId();
        sanitization.bypassSecurityTrustStyle("flex");
        setStyle(root.getStyle());
        
        switch (root.getSlotType()) {
            case splitHorizontal :
            case splitVertical :
                this.slots = new JsArray<>();
                for (int i = 0; i < root.getChildren().length(); i++) {
                    UiSlot slot = root.getChildren().get(i);
                    slots.push(slot);
                }
                break;
                
            case single :
            case tab :
            case tray :
                this.viewModels = new JsArray<>();
                for (int i = 0; i < root.getViewModels().length(); i++) {
                    UiView model = root.getViewModels().get(i);
                    viewModels.push(model);
                }
                setStyle(root.getStyle());
                break;
                
            default :
                break;
        }
                
        dispatchSubscr = dispatch.observable.subscribe(e -> {
            if (e.eventType.equals("view-close") && slotId.equals(e.data.get("slotId"))) {
                closeView(e.model);
            }
            else if (e.eventType.equals("view-detach") && slotId.equals(e.data.get("slotId"))) {
                detachView(e.model);
            }
            else if (e.eventType.equals("view-attach") && slotId.equals(e.data.get("slotId"))) {
                attachView(e.model);
            }
        });
    }
        
    protected void closeView(UiView model) {
        detachView(model);
//        model.destroy();
    }
    
    protected void detachView(UiView model) {
        if (model.isAttached()) {
            int idx = viewModels.indexOf(model);
            if (idx != -1) {
                viewModels.splice(idx, 1);
                model.setAttached(false);
                viewFactoryOutput.destroyView(model);
                ref.detectChanges();
            }
        }
        else {
            Logger.log("Warning: Trying to detach already detached view (" + model.getId() + ")!");
        }
    }

    protected void attachView(UiView model) {
        int idx = viewModels.indexOf(model);
        assert idx == -1;
        model.setActive(true);
        model.setAttached(true);
        viewModels.push(model);
        viewFactoryOutput.createView(model);
        ref.detectChanges();
        Scheduler.get().scheduleDeferred(() -> 
            JQuery.Helper.$("#" + model.getId()).attach(JQuery.Helper.$("cwf-slot#" + slotId + " > .section .content").first()));
     }

}