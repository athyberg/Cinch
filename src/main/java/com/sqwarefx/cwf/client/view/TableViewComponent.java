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

import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.transport.PerspectiveService;
import com.sqwarefx.cwf.client.transport.ViewEventDispatchService;
import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.widget.ContextMenuComponent;
import com.sqwarefx.cwf.client.widget.TableComponent;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.HTMLElement;
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
@Component( selector = "cwf-table-view", 
            template = "<cwf-table [model]='model' (contextmenu)='onContextMenu($event, $event.target); false'></cwf-table>"
                     + "<cwf-contextmenu *ngIf='contextMenuModel' [model]='contextMenuModel'></cwf-contextmenu>" )

@JsType
public class TableViewComponent extends ViewBase {
    
    @ViewChild(component = TableComponent.class)
    @JsProperty
    protected TableComponent table;

    @ViewChild(component = ContextMenuComponent.class)
    @JsProperty
    protected ContextMenuComponent ctxMenu;
    
    @JsProperty
    protected JsArray<String> contextMenuModel;
    
    public TableViewComponent(PerspectiveService perspectiveService, 
                              ViewEventDispatchService dispatch,
                              DictionaryPipe dictionary, 
                              ChangeDetectorRef changeDetectorRef,
                              ElementRef elementRef) {
        super(perspectiveService, dispatch, dictionary, changeDetectorRef, elementRef);
    }

    @Override
    protected void onViewResize(JsObject data) {
        super.onViewResize(data);
        int headingHeight = getOuterHeight(".panel-heading", false);
//        GWT.debugger();
        
        JQuery panelBody = get$().find(".panel-body");
        int padding = getIntValue(panelBody.innerHeight()) - getIntValue(panelBody.height());
        
        table.setSize(viewHeight - headingHeight - padding, viewWidth);
    }
    
    // TODO move to table... ???
    public void onContextMenu(Object event, HTMLElement target) {
//        Logger.log(target);
//        int clientX = ((Number) JsTools.getObjectProperty(event, "clientX")).intValue();
//        int clientY = ((Number) JsTools.getObjectProperty(event, "clientY")).intValue();
//        
//        contextMenuModel = JsArray.of("details", "add", "remove", null, "filter-by-value");
//        JQuery.Helper.$("body").on("click", (e, d) -> {
//            
//            // TODO e.target holds the clicked menuItem if any...
//            
//            contextMenuModel = null;
//            JQuery.Helper.$("body").off("click");
//        });
//        
//        Scheduler.get().scheduleDeferred(() -> {
//            Object offset = get$().offset();
//            int offsetTop = ((Number) JsTools.getObjectProperty(offset, "top")).intValue();
//            int offsetLeft = ((Number) JsTools.getObjectProperty(offset, "left")).intValue();
//            Object position = get$().position();
//            int positionTop = ((Number) JsTools.getObjectProperty(position, "top")).intValue();
//
//            get$().find(".context-menu").css("top", (clientY - offsetTop + positionTop) + "px");
//            get$().find(".context-menu").css("left", (clientX - offsetLeft) + "px");
//        });
    }
    
}