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

import com.sqwarefx.cwf.client.entity.UiTable;
import com.sqwarefx.cwf.client.pipes.DictionaryPipe;
import com.sqwarefx.cwf.client.util.JQuery;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.Event;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.EventEmitter;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import fr.lteconsulting.angular2gwt.ng.core.Output;
import fr.lteconsulting.angular2gwt.ng.core.ViewChild;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * Table toolbar component
 * 
 */
@Component( selector = "cwf-table-toolbar", 
            template = "<div class='well well-sm no-padding no-border no-background'>"
                     + "<div class='container-fluid no-padding'>"
                     + "<div class='badge'>{{model.totalSize}}</div>"
                     
                     // toolbar buttons
                     + "<ul class='nav nav-pills pull-right small'>"
                     + "<li><a class='optimize' title='{{ " + "\\\"optimize\\\"" + " | translate }}'><span class='glyphicon glyphicon-flash'></span></a></li>"
                     + "<li><a class='filter' title='{{ " + "\\\"filter\\\"" + " | translate }}'><span class='glyphicon glyphicon-filter'></span></a></li>"
                     + "<li><a class='export' title='{{ " + "\\\"export\\\"" + " | translate }}'><span class='glyphicon glyphicon-export'></span></a></li>"
                     + "<li #manage class='dropdown'><a class='manage' (click)='toggleManageMenu($event)' title='{{ " + "\\\"manage\\\"" + " | translate }}'>"
                     + "<span class='glyphicon glyphicon-cog'></span>&nbsp;<span class='caret'></span></a></li>"
                     + "</ul>"
                     + "</div>"
                     + "</div>" )

@JsType
public class TableToolbarComponent extends WidgetBase {
    
    @ViewChild (selector = "manage")
    @JsProperty
    protected ElementRef manageColumnsBtn;
    
    @Input
    @JsProperty
    public UiTable model;
    
    @Output
    @JsProperty
    protected final EventEmitter<String> action = new EventEmitter<>();

    protected JsObject manageColumnsOptions;

    protected JQuery manageBtn;

    public TableToolbarComponent(ChangeDetectorRef changeDetectorRef, ElementRef elementRef, 
        DictionaryPipe dictionary) {
        super(changeDetectorRef, elementRef);
    }
    
    @Override
    public void ngOnInit() {
        manageColumnsOptions = new JsObject();
        
        JsArray<String> opts = JsArray.of(model.getHeaders());
        opts.push(null);
        opts.push("Reset table");
        
        manageColumnsOptions.set("options", opts);
        manageColumnsOptions.set("sortable", (UiEventHandler<Object, Object>) (e, ui) -> sort(e, ui));
        manageColumnsOptions.set("toggle", (UiEventHandler<Object, Object>) (e, ui) -> toggle(e, ui));
        manageColumnsOptions.set("select", (UiEventHandler<Object, Object>) (e, ui) -> select(e, ui));

        manageBtn = JQuery.Helper.$(manageColumnsBtn.nativeElement());
    }

    public void toggleManageMenu(Event event) {
        Logger.log("-----------> toggle");
        event.stopPropagation();
        if (manageBtn.hasClass("open")) {
            closeMenu();
        }
        else {
            JQuery contextmenu = manageBtn.contextmenu(manageColumnsOptions);
            contextmenu.on("menu-close", (e, ui) -> closeMenu());
            manageBtn.addClass("open");
        }
    }

    protected void select(Object event, Object ui) {
//        closeMenu();
    }

    protected void toggle(Object event, Object ui) {
        closeMenu();
//        int index = Integer.parseInt(JQuery.Helper.$(target).attr("value"));
//        int column = getIntValue(model.columnOrder[index]);
//        model.columnVisible[column] = !model.columnVisible[column];
//        action.emit("manage");        
    }
    
    protected void sort(Object event, Object ui) {
        closeMenu();
//        ccDropdown.find("ul > li").each((JQueryFunction<Integer, HTMLElement>) (i, e) -> {         
//            int col = ((Number) i).intValue();
//            int index = ((Number) JQuery.Helper.$(e).data("index")).intValue();
////            model.columnOrder[col] = index;
//        });
//        action.emit("manage");        
    }
       
    protected void closeMenu() {
        manageBtn.contextmenu(null);
        manageBtn.removeClass("open");
    }
    
}