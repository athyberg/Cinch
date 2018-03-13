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
package com.sqwarefx.cwf.client.transport;

import java.util.ArrayList;
import java.util.List;

import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Observable;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Subject;
import fr.lteconsulting.angular2gwt.ng.core.Injectable;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@Injectable
@JsType
public class Bootstrap {
    protected Subject<Integer> progressValue = new Subject<>();
    public Observable<Integer> progress = progressValue.asObservable();    
    protected List<Service> services = new ArrayList<>();
    protected int serviceCnt;
    protected boolean done;

    public void register(Service service) {
        services.add(service);
        serviceCnt++;
        progress();
    }
    
    public boolean markAsReady(Service service) {
        boolean remove = services.remove(service);
        progress();
        return remove;
    }

    @JsMethod
    public boolean isReady() {
        return done;
    }
    
    protected void progress() {
        int value = (100 * (serviceCnt - services.size())) / serviceCnt;
        progressValue.next(value);
        if (value == 100) {
            done = true;
        }
    }
    
    
    
}
