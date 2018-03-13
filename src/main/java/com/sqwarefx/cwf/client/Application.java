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


import com.google.gwt.core.client.EntryPoint;

import fr.lteconsulting.angular2gwt.client.interop.ng.platformBrowserDynamic.PlatformBrowserDynamic;

/**
 * This is the application entry point. It just bootstraps Angular...
 */
public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {
        /**
         * You can uncomment that line to switch Angular to Production mode The Core class' fqn is
         * fr.lteconsulting.angular2gwt.client.interop.ng.core.Core
         */
        // Core.enableProdMode();
        
        /**
         * Here we just bootstrap the Angular 2 framework with our application module.
         *
         * The application module is implemented in the {@link ApplicationModule} class
         */
        PlatformBrowserDynamic.platformBrowserDynamic()
            .bootstrapModule(ApplicationModule_AngularModule.getNgModulePrototype());
        
        
        
//        char _q = '"';
//        char _c = ':';
//        char _d = ',';
//        
//        StringBuilder s = new StringBuilder("{");
//        s.append(_q).append("_M$ID_").append(_q).append(_c).append(_q).append("Perspective").append(_q).append(_d);
//        s.append(_q).append("perspectiveId").append(_q).append(_c).append(_q).append("referenceData").append(_q).append(_d);
//        s.append(_q).append("root").append(_q).append(_c).append('{');
//        s.append(_q).append("slotId").append(_q).append(_c).append(_q).append("footer").append(_q);
//        s.append('}');
//        s.append('}');
//
//        
//        GWT.debugger();
//
//        Logger.log(s.toString());
//        
//        UiPerspective perspective = JSON.parse(s.toString());
//        String perspectiveId = perspective.getPerspectiveId();
//        Logger.log(perspectiveId);
//        
//        UiSlot root = perspective.getRoot();
//        Logger.log(root.getSlotId());
//
//        Logger.log(perspective.getMetaDataId());
        
        
    }


}