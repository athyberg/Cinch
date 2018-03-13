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
package com.sqwarefx.cwf.client;

import com.sqwarefx.cwf.client.transport.Bootstrap;

import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Subscription;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnDestroy;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import fr.lteconsulting.angular2gwt.ng.core.HostBinding;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * Application Component
 * 
 */
@Component( selector = "cwf-app", 
            template = "<cwf-navbar></cwf-navbar>"
                     + "<router-outlet></router-outlet>" )
@JsType
public class ApplicationComponent implements OnInit, OnDestroy {
    
    @HostBinding("class")
    @JsProperty
    protected String className = "application";
    
    @JsProperty
    protected int progress;
    
    protected Bootstrap bootstrap;

    protected Subscription subscription;
    
    public ApplicationComponent(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }
    
    @Override
    public void ngOnInit() {
        subscription = bootstrap.progress.subscribe(p -> progress = p);
    }

    @Override
    public void ngOnDestroy() {
        subscription.unsubscribe();
    }
    
}
