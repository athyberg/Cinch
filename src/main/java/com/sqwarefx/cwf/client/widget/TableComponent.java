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
package com.sqwarefx.cwf.client.widget;

import com.google.gwt.core.client.Scheduler;
import com.sqwarefx.cwf.client.entity.UiTable;
import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.util.JQueryFunction;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.JsTools;
import fr.lteconsulting.angular2gwt.client.interop.HTMLElement;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * View component
 * 
 */
@Component( selector = "cwf-table", 
            templateUrl = "table/table.component.html")

@JsType
public class TableComponent extends WidgetBase {
    
    @JsProperty
    @Input
    public UiTable model;

//    @HostBinding("attr.fit")
//    @JsProperty
//    protected String fit = ""; 

    @JsProperty
    protected String[] header;
    
    @JsProperty
    protected JsArray<String[]> rows = new JsArray<>();

    protected JQuery toolbar; 
    protected JQuery scrollbarVertical; 
    protected JQuery scrollbarHorizontal; 
    protected JQuery scroll; 
    protected JQuery table; 
    protected JQuery headerRow; 
    protected JQuery footerRow; 

    protected static final String NBSP = "\u00A0";
    
    protected String[] empty;
    
    protected int componentHeight;
    protected int headerHeight;
    protected int rowHeight;

        
    public TableComponent(ChangeDetectorRef changeDetectorRef, ElementRef elementRef) {
        super(changeDetectorRef, elementRef);
    }
    
    @Override
    public void ngOnInit() {
        
        // create an empty table row
        header = new String[model.getHeaders().length];
        empty = new String[model.getHeaders().length];
        for (int i = 0; i < model.getHeaders().length; i++) {
            int index = getIntValue(model.getColumnOrder()[i]);
            header[i] = model.getHeaders()[index];
            empty[i] = NBSP;
        }

        // add an empty row initially just to be able to find out the table row height
        rows.push(empty); 
        
        Scheduler.get().scheduleDeferred(() -> {
            toolbar = get$().find("cwf-table-toolbar > div");
            scrollbarVertical = get$().find(".scrollbar-vertical"); 
            scrollbarHorizontal = get$().find(".scrollbar-horizontal"); 
            scroll = get$().find("cwf-table-toolbar + div"); 
            table = get$().find("table"); 
            headerRow = table.find("thead tr"); 
            footerRow = table.find("tfoot tr"); 
            
            headerRow.children(":not(:last-child)").each((JQueryFunction<Integer, HTMLElement>) (i, e) -> {
                JsObject options = new JsObject();
                options.set("handles", "e");
                JQuery.Helper.$(e).resizable(options);
            });
            
        });
    }
        
    @Override
    public void setSize(int height, int width) {
        componentHeight = height;
        redraw();
    }
    
    // TODO! use double 0.0-1.0
    public void onScrollVertical(int percent) {
        Logger.log("scroll vert percent: " + percent + "%");
        model.setPosition((model.getTotalSize() - model.getViewportSize()) * percent / 100);
        if (model.getPosition() > model.getTotalSize() - model.getViewportSize()) {
            model.setPosition(model.getTotalSize() - model.getViewportSize());
        }
        redraw();
    }

    public void onScrollHorizontal(int percent) {
        Logger.log("scroll horiz percent: " + percent + "%");
        int translate = percent * (getIntValue(table.outerWidth()) - getIntValue(scroll.outerWidth())) / 100;
        table.css("transform", "translateX(-" + translate  + "px)");
    }
    
    public void onAction(String action) {
        for (int col = 0; col < header.length; col++) {
            header[col] = model.getHeaders()[getIntValue(model.getColumnOrder()[col])];
        }
        redraw();
    }
    
    public void onMouseEnter(Object event) {
        get$().find(".scrollbar").css("opacity", "1");
    }

    public void onMouseLeave(Object event) {
        get$().find(".scrollbar").css("opacity", "");
    }
    
    public void onMouseWheel(Object event) {
        int delta = ((Number) JsTools.getObjectProperty(event, "deltaY")).intValue();
        if (delta < 0) {
            model.setPosition(Math.max(model.getPosition() - 3, 0));
        }
        else if (delta > 0) {
            model.setPosition(Math.min(model.getPosition() + 3, model.getTotalSize() - model.getViewportSize()));
        }
        redraw();
    }
    
    public String getStyle(int i) {
        // TODO add style depending on metadata
        return model.getColumnVisible()[i] ? "" : "hidden";
    }
    
    protected void redraw() {
        
//        if (false /*ismobile*/) {
//            table.removeClass("table-condensed");
//        }
//        else {
//            table.addClass("table-condensed");
//        }
        
        int toolbarHeight = getIntValue(toolbar.outerHeight(true));
        int headerRowHeight = getIntValue(headerRow.outerHeight());
        int bodyRowHeight = getIntValue(table.find("tbody tr:first-child").outerHeight());
        int footerRowHeight = getIntValue(footerRow.outerHeight());
        
        int totalTableHeight = model.getTotalSize() * bodyRowHeight;
        
        int tableWidth = getIntValue(table.outerWidth());
        int scrollWidth = getIntValue(scroll.outerWidth());
        
        // TODO: also incl horizontal scrollbar...
        model.setViewportSize(Math.max((componentHeight - toolbarHeight - headerRowHeight - footerRowHeight) / bodyRowHeight, 1));
        
        int viewportTableHeight = model.getViewportSize() * bodyRowHeight;

        if (rows.length() > model.getViewportSize()) {
            // TODO! make sure we always have at least 1 row
            rows.splice(model.getViewportSize(), rows.length() - model.getViewportSize());
        }
        else { 
            rows = JsArray.empty();
            for (int row = 0; row < model.getViewportSize(); row++) {
                if (row + model.getPosition() < model.getTotalSize()) {
                    // Create fake grid cell data...
                    String[] cellData = new String[model.getHeaders().length];
                    for (int col = 0; col < model.getHeaders().length; col++) {
                        int index = getIntValue(model.getColumnOrder()[col]);
                        cellData[col] = "Cell " + (row + model.getPosition()) + ":" + index;
                    }
                    rows.push(cellData);
                }
                else {
                    rows.push(empty);
                }
            }
        }
        
        scrollbarVertical.css("top", headerRowHeight + "px");
        scroll.css("height", ((model.getViewportSize() * bodyRowHeight) + headerRowHeight + footerRowHeight + 1) + "px");

        if (tableWidth > scrollWidth) {
            scrollbarHorizontal.css("display", "block");
            scrollbarHorizontal.children().css("width", (100 * scrollWidth / tableWidth) + "%");
        }
        else {
            scrollbarHorizontal.css("display", "");
        }

        if (totalTableHeight > viewportTableHeight) {
            scrollbarVertical.css("display", "block");
            int handleHeight = Math.max(viewportTableHeight * viewportTableHeight / totalTableHeight, bodyRowHeight);
            scrollbarVertical.children().css("height", handleHeight + "px");
        }
        else {
            scrollbarVertical.css("display", "");
        }

        changeDetectorRef.detectChanges();
    }

}