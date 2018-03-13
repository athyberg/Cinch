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

import com.sqwarefx.cwf.client.entity.UiView;

import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Observable;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Subject;
import fr.lteconsulting.angular2gwt.ng.core.Injectable;
import jsinterop.annotations.JsType;

/**
 *
 * View event service
 * 
 */
@Injectable
@JsType
public class ViewEventDispatchService {

    public Subject<Event> fire = new Subject<>();
    public Observable<Event> observable = fire.asObservable();
    
    public void fireEvent(String eventType, UiView view, JsObject data) {
        fire.next(new Event(eventType, view, data));
    }
    
    @JsType
    public static class Event {
        public String eventType;
        public UiView model;
        public JsObject data;
        public Event(String eventType, UiView model, JsObject data) {
            this.eventType = eventType;
            this.model = model;
            this.data = data;
        }
    }
}