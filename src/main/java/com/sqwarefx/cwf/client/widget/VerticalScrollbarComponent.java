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
import com.sqwarefx.cwf.client.util.JQuery;

import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.JsTools;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ChangeDetectorRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.ElementRef;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.EventEmitter;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import fr.lteconsulting.angular2gwt.ng.core.Output;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
*
* Vertical scrollbar component
* 
*/
@Component( selector = "cwf-scrollbar-vertical", 
            template = "<div></div>" )

@JsType
public class VerticalScrollbarComponent extends WidgetBase {

    @Output
    @JsProperty
    protected final EventEmitter<Integer> scroll = new EventEmitter<>();

    @HostBinding("class")
    @JsProperty
    protected String className = "scrollbar scrollbar-vertical";

    protected JQuery handle;
    
    public VerticalScrollbarComponent(ChangeDetectorRef changeDetectorRef, ElementRef elementRef) {
        super(changeDetectorRef, elementRef);
    }
    
    @Override
    public void ngOnInit() {
        handle = get$().children();
        Scheduler.get().scheduleDeferred(() -> makeDraggable());
    }
    
    protected void makeDraggable() {
        JsObject options = new JsObject();
        options.set("axis", "y");
        options.set("containment", get$());
        options.set("drag", (UiEventHandler<Object, Object>) (e, ui) -> handleDrag(e, ui)); 
        handle.draggable(options);
    }

    protected void handleDrag(Object event, Object ui) {        
        int scrollbarHeight = getIntValue(get$().outerHeight());
        int scrollbarHandleHeight = getIntValue(handle.outerHeight());
        int span = scrollbarHeight - scrollbarHandleHeight;
        Object position = JsTools.getObjectProperty(ui, "position");
        int top = getIntValue(JsTools.getObjectProperty(position, "top"));
        scroll.emit(top * 100 / span);
    }

}
