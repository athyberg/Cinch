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

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.Input;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@Component( selector = "cwf-contextmenu", 
            template = "<ul class='context-menu dropdown-menu'>"
                     + "<ng-container *ngFor='let menuItem of model' [ngSwitch]='menuItem'>"
                     + "<li class='divider' *ngSwitchWhen='undefined'></li>"
                     + "<li *ngSwitchDefault><a href='#' class='menuitem'>{{ menuItem | translate }}</a></li>"
                     + "</ng-container>"
                     + "</ul>" )

@JsType
public class ContextMenuComponent implements OnInit {

    @Input
    @JsProperty
    protected JsArray<String> model;

    public ContextMenuComponent() {
    }

    @Override
    public void ngOnInit() {
    }

}
