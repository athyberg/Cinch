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

import java.util.HashMap;
import java.util.Map;

import com.sqwarefx.cwf.client.entity.UiLayout;
import com.sqwarefx.cwf.client.entity.UiPerspective;
import com.sqwarefx.cwf.client.entity.event.PerspectiveLayoutChangeEvent;
import com.sqwarefx.cwf.client.util.Logger;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.angular.rxjs.Observable;
import fr.lteconsulting.angular2gwt.client.interop.ng.http.Headers;
import fr.lteconsulting.angular2gwt.client.interop.ng.http.Http;
import fr.lteconsulting.angular2gwt.client.interop.promise.Promise;
import fr.lteconsulting.angular2gwt.ng.core.Injectable;
import jsinterop.annotations.JsType;

/**
 *
 * Perspective service
 * 
 */
@Injectable
@JsType
public class PerspectiveService implements Service {

    private static final String endpointUrl = "app/perspective"; // URL to web api
    private static final Headers headers = new Headers();
    static {
        headers.append("Content-Type", "application/json");
    }

    protected Http http;

    protected Map<String, UiPerspective> perspectiveMap = new HashMap<>();
//    protected Map<String, PerspectiveSlot> slotMap = new HashMap<>();
//    protected Map<String, ViewModel> viewMap = new HashMap<>();

//    protected int nbOfAvailPerspectives;

//    public boolean perspectivesLoaded;
//    public Subject<Integer> progressValue = new Subject<>();
//    public Observable<Integer> loadProgress = progressValue.asObservable();
    
//    protected Bootstrap bootstrap;
    
    public PerspectiveService(Http http, final Bootstrap bootstrap) {
        this.http = http;
        bootstrap.register(this);
        
        // load available perspectives
        getPerspectives().then(p -> {
            for(int i = 0; i < p.length(); i++) {
                UiPerspective perspective = p.get(i);
                perspectiveMap.put(perspective.getPerspectiveId(), perspective);
            }
            bootstrap.markAsReady(PerspectiveService.this);
            return p;
        });
    }
    
    
    protected Promise<JsArray<UiPerspective>> getPerspectives() {
        return http.get(endpointUrl)
            .toPromise()
            .<JsArray<UiPerspective>>then(response -> response.json())
            .onCatch(this::handleError);
    }


    public Observable<JsArray<JsObject>> getAvailablePerspectives() {
        return http.get(endpointUrl + "/list").map(response -> response.json());
    }

    public UiPerspective getPerspective(String perspectiveId) {
        return perspectiveMap.get(perspectiveId);
    }


    public Promise<UiPerspective> updatePerspective(PerspectiveLayoutChangeEvent event) {
        
//        GWT.debugger();
        
        // change local values
        for (UiLayout layout : event.getLayout()) {
            setSlotStyle(event.getPerspectiveId(), layout.getSlotId(), layout.getStyle());
        }
        
        // update server values
//        String url = endpointUrl + "/" + event.getPerspectiveId();
//        String data = JSON.stringify(event);
//        JsObject options = new JsObject().set("headers", headers);
        return null;
//            http.put(url, data, options)
//            .toPromise()
//            .<UiPerspective>then(response -> response.json())
//            .onCatch(this::handleError);
    }

    protected void setSlotStyle(String perspectiveId, String slotId, String style) {
    }

    protected Promise<?> handleError(Object error) {
        Logger.log("An error occurred: " + error); // FIXME! for demo purposes only
        return Promise.reject(error);
    }

//    public void setActiveView(Object tab) {
//        Logger.log(tab);
//    }
//
//    public ViewModel getView(String viewId) {
//        return viewMap.get(viewId);
//    }
//
//    protected void mapify(PerspectiveSlot root) {
//        slotMap.put(root.slotId, root);
//        for (PerspectiveSlot slot : root.children) {
//            mapify(slot);
//        }
//        for (ViewModel _model : root.viewModels) {
//            ViewModel model = JsTools.convertObject("com.sqwarefx.common.perspective.ViewModel", _model);
//            viewMap.put(model.id, model);
//        }
//    }

}